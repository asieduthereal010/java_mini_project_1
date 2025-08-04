package com.example.demo.services.student;

import com.example.demo.dtos.StudentDto;
import com.example.demo.dtos.StudentDashboardDTO;
import com.example.demo.dtos.PaymentHistoryDTO;
import com.example.demo.dtos.CourseEnrollmentDTO;
import com.example.demo.dtos.FeesOverviewDTO;
import com.example.demo.dtos.SemesterDTO;
import com.example.demo.dtos.LecturerDto;
import com.example.demo.exceptions.StudentNotFoundException;
import com.example.demo.models.Students;
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
import java.util.stream.Collectors;

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
        
        // Mock data for demonstration - in real implementation, fetch from repositories
        List<CourseEnrollmentDTO> courses = getMockCourses();
        FeesOverviewDTO fees = getMockFees();
        SemesterDTO semester = getMockSemester();
        
        return new StudentDashboardDTO(
                student.getId(),
                student.getName(),
                student.getEmail(),
                student.getDate_of_birth(),
                courses,
                fees,
                semester
        );
    }

    @Override
    public PaymentHistoryDTO getPaymentHistory(String studentId) {
        // Verify student exists
        studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Student not found"));
        
        // Mock data for demonstration - in real implementation, fetch from paymentsRepository
        List<PaymentHistoryDTO.PaymentDetailDTO> payments = getMockPayments(studentId);
        PaymentHistoryDTO.PaymentSummaryDTO summary = getMockPaymentSummary(payments);
        
        return new PaymentHistoryDTO(payments, summary);
    }

    @Override
    public Object getFeesOverview(String studentId) {
        // Verify student exists
        Students student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Student not found"));
        
        // Mock data for demonstration - in real implementation, fetch from repositories
        return createMockFeesOverview(student);
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

    private FeesOverviewDTO getMockFees() {
        return new FeesOverviewDTO(
                new BigDecimal("5000.00"),
                new BigDecimal("3000.00"),
                new BigDecimal("2000.00"),
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
