package com.example.demo.services.student;

import com.example.demo.dtos.StudentDto;
import com.example.demo.dtos.StudentDashboardDTO;
import com.example.demo.dtos.PaymentHistoryDTO;
import com.example.demo.dtos.CourseEnrollmentDTO;
import com.example.demo.dtos.FeesDTO;
import com.example.demo.dtos.SemesterDTO;
import com.example.demo.dtos.LecturerDto;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.exceptions.StudentNotFoundException;
import com.example.demo.models.*;
import com.example.demo.requests.students.ProfileUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.demo.repositories.StudentRepository;
import com.example.demo.repositories.PaymentsRepository;
import com.example.demo.repositories.CourseEnrollmentRepository;
import com.example.demo.repositories.SemestersRepository;
import com.example.demo.repositories.FeeRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.ArrayList;

@RequiredArgsConstructor
@Service
public class StudentsService implements IStudentsService {
    private final StudentRepository studentRepository;
    private final PaymentsRepository paymentsRepository;
    private final CourseEnrollmentRepository courseEnrollmentRepository;
    private final SemestersRepository semestersRepository;
    private final FeeRepository feeRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<StudentDashboardDTO> getStudentDashboardData() {
        // This method is kept for backward compatibility
        // The new getStudentDashboard(String studentId) method should be used instead
        List<Object[]> results = studentRepository.fetchStudentDashboardData();
        return results.stream().map(row -> {
            // Create a simplified dashboard for the old method
            StudentDashboardDTO dashboard = new StudentDashboardDTO();
            dashboard.setId((String) row[0]);
            dashboard.setName((String) row[1]);
            // Set other fields to null for backward compatibility
            return dashboard;
        }).collect(Collectors.toList());
    }

    @Override
    public StudentDashboardDTO getStudentDashboard(String studentId) {
        Students student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Student not found"));

        // Get student's course enrollments
        List<CourseEnrollments> courseEnrollments = new ArrayList<>();
        try {
            courseEnrollments = courseEnrollmentRepository.findAllByStudentId(studentId)
                    .orElse(new ArrayList<>());
        } catch (Exception e) {
            // If no enrollments found, return empty list
        }
        
        // Convert enrollments to DTOs
        List<CourseEnrollmentDTO> courseEnrollmentDTOs = courseEnrollments.stream()
                .map(enrollment -> {
                    CourseEnrollmentDTO dto = new CourseEnrollmentDTO();
                    dto.setId(enrollment.getCourse().getId());
                    dto.setName(enrollment.getCourse().getName());
                    dto.setCode(enrollment.getCourse().getCode());
                    
                    // Get lecturer for this course
                    if (enrollment.getCourse().getLecturers() != null && !enrollment.getCourse().getLecturers().isEmpty()) {
                        Lecturers lecturer = enrollment.getCourse().getLecturers().iterator().next();
                        dto.setLecturer(new LecturerDto(lecturer.getId(), lecturer.getName(), lecturer.getEmail()));
                    } else {
                        dto.setLecturer(new LecturerDto("N/A", "TBD", "tbd@university.edu"));
                    }
                    
                    dto.setProgress(75); // Default progress - you might want to add this field to the CourseEnrollments model
                    dto.setSemester(enrollment.getSemester() != null ? enrollment.getSemester().getName() : "Unknown");
                    dto.setStatus(enrollment.getStatus());
                    dto.setGrade(enrollment.getGrade());
                    
                    return dto;
                })
                .collect(Collectors.toList());
        
        // Get current semester (active semester)
        Semesters currentSemester = semestersRepository.findAll().stream()
                .filter(Semesters::isActive)
                .findFirst()
                .orElse(null);
        
        SemesterDTO semesterDTO;
        if (currentSemester != null) {
            SemesterDTO.CurrentSemesterDTO current = new SemesterDTO.CurrentSemesterDTO(
                    currentSemester.getId().intValue(),
                    currentSemester.getName(),
                    currentSemester.getSemesterType(),
                    currentSemester.getYear(),
                    currentSemester.getStartDate(),
                    currentSemester.getEndDate(),
                    currentSemester.isActive()
            );
            semesterDTO = new SemesterDTO(current);
        } else {
            semesterDTO = getMockSemester(); // Fallback to mock if no active semester
        }
        
        // Get student's fees
        FeesDTO feesDTO;
        try {
            List<Fees> fees = feeRepository.findAllByStudentId(studentId)
                    .orElse(new ArrayList<>());
            
            if (!fees.isEmpty()) {
                Fees fee = fees.get(0); // Get the first fee record
                feesDTO = modelMapper.map(fee, FeesDTO.class);
                feesDTO.setAmountOwed(fee.getTotalAmount().subtract(fee.getAmountPaid()));
            } else {
                feesDTO = new FeesDTO();
            }
        } catch (Exception e) {
            feesDTO = new FeesDTO();
        }
        
        return new StudentDashboardDTO(
                student.getId(),
                student.getName(),
                student.getEmail(),
                student.getDate_of_birth(),
                courseEnrollmentDTOs,
                feesDTO,
                semesterDTO
        );
    }

    @Override
    public PaymentHistoryDTO getPaymentHistory(String studentId) {
        // Verify student exists
        studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Student not found"));
        
        // Get real payment data from repository
        List<Payments> paymentsList = paymentsRepository.findAllByStudentId(studentId);
        
        if (paymentsList.isEmpty()) {
            // Return empty payment history if no payments found
            return new PaymentHistoryDTO(new ArrayList<>(), 
                new PaymentHistoryDTO.PaymentSummaryDTO(BigDecimal.ZERO, 0, LocalDateTime.now()));
        }
        
        // Convert payments to DTOs
        List<PaymentHistoryDTO.PaymentDetailDTO> paymentDTOs = paymentsList.stream()
                .map(payment -> {
                    PaymentHistoryDTO.PaymentDetailDTO dto = new PaymentHistoryDTO.PaymentDetailDTO();
                    dto.setId(payment.getTransactionId());
                    dto.setStudentId(payment.getStudent().getId());
                    dto.setAmount(payment.getAmount());
                    dto.setPaymentMethod(payment.getPaymentMethod());
                    dto.setPaymentDate(payment.getPaymentDate());
                    dto.setTransactionId(payment.getTransactionId());
                    dto.setStatus(payment.getStatus());
                    dto.setDescription(payment.getDescription());
                    dto.setFeeId(payment.getFees() != null ? payment.getFees().getId().intValue() : null);
                    dto.setSemester("Unknown"); // Default semester name
                    return dto;
                })
                .collect(Collectors.toList());
        
        // Calculate payment summary
        BigDecimal totalPaid = paymentDTOs.stream()
                .map(PaymentHistoryDTO.PaymentDetailDTO::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        LocalDateTime lastPaymentDate = paymentDTOs.stream()
                .map(PaymentHistoryDTO.PaymentDetailDTO::getPaymentDate)
                .max(LocalDateTime::compareTo)
                .orElse(LocalDateTime.now());
        
        PaymentHistoryDTO.PaymentSummaryDTO summary = new PaymentHistoryDTO.PaymentSummaryDTO(
                totalPaid,
                paymentDTOs.size(),
                lastPaymentDate
        );
        
        return new PaymentHistoryDTO(paymentDTOs, summary);
    }

    @Override
    public Object getFeesOverview(String studentId) {
        // Verify student exists
        Students student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Student not found"));
        
        // Get real fees data from repository
        List<Fees> feesList = new ArrayList<>();
        try {
            feesList = feeRepository.findAllByStudentId(studentId)
                    .orElse(new ArrayList<>());
        } catch (Exception e) {
            // If no fees found, return empty list
        }
        
        // Calculate total amounts
        BigDecimal calculatedTotalAmount = feesList.stream()
                .map(Fees::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal calculatedTotalPaid = feesList.stream()
                .map(Fees::getAmountPaid)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal calculatedTotalOwed = calculatedTotalAmount.subtract(calculatedTotalPaid);
        
        // Get the most recent due date
        LocalDate calculatedDueDate = feesList.stream()
                .map(Fees::getDueDate)
                .max(LocalDate::compareTo)
                .orElse(LocalDate.of(2024, 12, 31));
        
        // Get academic year and semester
        String calculatedAcademicYear = feesList.isEmpty() ? "2024-2025" : feesList.get(0).getAcademicYear();
        String calculatedSemester = feesList.isEmpty() ? "Fall 2024" : feesList.get(0).getSemester().getName();
        
        // Create fees overview object
        return new Object() {
            public final String studentId = student.getId();
            public final String studentName = student.getName();
            public final BigDecimal totalAmount = calculatedTotalAmount;
            public final BigDecimal amountPaid = calculatedTotalPaid;
            public final BigDecimal amountOwed = calculatedTotalOwed;
            public final LocalDate dueDate = calculatedDueDate;
            public final String academicYear = calculatedAcademicYear;
            public final String semester = calculatedSemester;
        };
    }

    @Override
    public StudentDto updateStudentDetails(ProfileUpdateRequest request) {
        Students student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new StudentNotFoundException("No student found by that ID"));
        student.setName(request.getName());
        student.setEmail(request.getEmail());
        student.setDate_of_birth(request.getDateOfBirth());
        studentRepository.save(student);
        return modelMapper.map(student, StudentDto.class);
    }

    @Override
    public StudentDto getStudentDetailsById(String studentId) {
        Students student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Student not found"));
        return modelMapper.map(student, StudentDto.class);
    }

    @Override
    public List<StudentDto> getAllStudents() {
        List<Students> students = studentRepository.findAll();
        return students.stream().map(this::convertToDto).toList();
    }

    private StudentDto convertToDto(Students student) {
        return modelMapper.map(student, StudentDto.class);
    }

    // Mock data methods for demonstration
    private List<CourseEnrollmentDTO> getMockCourses() {
        return Arrays.asList(
                new CourseEnrollmentDTO("CS101", "Introduction to Computer Science", "CS101", 
                        new LecturerDto("LEC001", "Dr. Sarah Johnson", "sarah.johnson@university.edu"), 
                        75, "Fall 2024", "active", null),
                new CourseEnrollmentDTO("MATH201", "Advanced Mathematics", "MATH201", 
                        new LecturerDto("LEC002", "Prof. Michael Chen", "michael.chen@university.edu"), 
                        60, "Fall 2024", "active", null),
                new CourseEnrollmentDTO("ENG101", "English Composition", "ENG101", 
                        new LecturerDto("LEC003", "Dr. Emily Davis", "emily.davis@university.edu"), 
                        90, "Fall 2024", "active", null)
        );
    }

    private FeesDTO getMockFees() {
        return new FeesDTO(
                1L,
                new BigDecimal("5500.00"),
                new BigDecimal("3000.00"),
                new BigDecimal("2500.00"),
                LocalDate.of(2024, 12, 31),
                "2024-2025",
                "Fall 2024"
        );
    }

    private SemesterDTO getMockSemester() {
        SemesterDTO.CurrentSemesterDTO current = new SemesterDTO.CurrentSemesterDTO(
                1, "Fall 2024", "Fall", 2024, 
                LocalDate.of(2024, 9, 1), 
                LocalDate.of(2024, 12, 15), 
                true
        );
        return new SemesterDTO(current);
    }

    private List<PaymentHistoryDTO.PaymentDetailDTO> getMockPayments(String studentId) {
        return Arrays.asList(
                new PaymentHistoryDTO.PaymentDetailDTO("PAY001", studentId, new BigDecimal("1500.00"), 
                        "credit_card", LocalDateTime.of(2024, 1, 15, 10, 30), "TXN20240115001", 
                        "completed", "Tuition Fee Payment", 1, "Fall 2024"),
                new PaymentHistoryDTO.PaymentDetailDTO("PAY002", studentId, new BigDecimal("1000.00"), 
                        "bank_transfer", LocalDateTime.of(2024, 2, 20, 14, 15), "TXN20240220001", 
                        "completed", "Course Registration Fee", 1, "Fall 2024"),
                new PaymentHistoryDTO.PaymentDetailDTO("PAY003", studentId, new BigDecimal("500.00"), 
                        "credit_card", LocalDateTime.of(2024, 3, 10, 9, 45), "TXN20240310001", 
                        "completed", "Partial Tuition Payment", 1, "Fall 2024")
        );
    }

    private PaymentHistoryDTO.PaymentSummaryDTO getMockPaymentSummary(List<PaymentHistoryDTO.PaymentDetailDTO> payments) {
        BigDecimal totalPaid = payments.stream()
                .map(PaymentHistoryDTO.PaymentDetailDTO::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        return new PaymentHistoryDTO.PaymentSummaryDTO(
                totalPaid,
                payments.size(),
                payments.get(payments.size() - 1).getPaymentDate()
        );
    }

    private Object createMockFeesOverview(Students student) {
        // This would return a comprehensive fee breakdown object
        // For now, returning a simple structure
        return new Object() {
            public final String studentId = student.getId();
            public final String studentName = student.getName();
            public final BigDecimal totalAmount = new BigDecimal("5000.00");
            public final BigDecimal amountPaid = new BigDecimal("3800.00");
            public final BigDecimal amountOwed = new BigDecimal("1200.00");
            public final LocalDate dueDate = LocalDate.of(2024, 12, 31);
        };
    }
}
