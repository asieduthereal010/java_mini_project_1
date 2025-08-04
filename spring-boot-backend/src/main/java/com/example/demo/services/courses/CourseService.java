package com.example.demo.services.courses;

import com.example.demo.dtos.*;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.exceptions.StudentNotFoundException;
import com.example.demo.exceptions.RegistrationValidationException;
import com.example.demo.exceptions.ScheduleConflictException;
import com.example.demo.exceptions.AlreadyEnrolledException;
import com.example.demo.models.*;
import com.example.demo.repositories.CourseEnrollmentRepository;
import com.example.demo.repositories.CourseRepository;
import com.example.demo.repositories.SemestersRepository;
import com.example.demo.repositories.StudentRepository;
import com.example.demo.repositories.LecturerRepository;
import com.example.demo.requests.course_enrollments.DeleteCourse;
import com.example.demo.requests.course_enrollments.RegisterCourses;
import com.example.demo.requests.courses.CourseRegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CourseService implements ICourseService {
    private final StudentRepository studentRepository;
    private final SemestersRepository semestersRepository;
    private final CourseRepository courseRepository;
    private final CourseEnrollmentRepository courseEnrollmentRepository;
    private final LecturerRepository lecturerRepository;
    private final ModelMapper modelMapper;

    @Override
    public Object getAvailableCourses(String studentId) {
        // Verify student exists
        studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Student not found"));

        
        // Mock data for demonstration - in real implementation, fetch from repositories
        AvailableCoursesResponseDTO response = new AvailableCoursesResponseDTO();
        response.setSemesters(getMockSemesters());
        response.setAvailableCourses(getMockAvailableCourses());
        response.setStudentEnrollments(getMockStudentEnrollments());
        
        return response;
    }

    @Override
    public Object calculateFees(String studentId, Integer semesterId, String courseIds) {
        // Verify student exists
        studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Student not found"));
        
        // Verify semester exists
        semestersRepository.findById(semesterId.longValue())
                .orElseThrow(() -> new ResourceNotFoundException("Semester not found"));
        
        // Mock data for demonstration - in real implementation, calculate from actual course fees
        return createMockFeeCalculation(studentId, semesterId, courseIds);
    }

    @Override
    public void registerCourses(CourseRegistrationRequest request) {
        Students student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new StudentNotFoundException("No student exists by that ID"));

        Semesters semester = semestersRepository.findById(request.getSemesterId())
                .orElseThrow(() -> new ResourceNotFoundException("No semester exists by that ID"));

        for (var req : request.getCourses()) {
            Courses course = courseRepository.findById(req.getCourseId())
                    .orElseThrow(() -> new ResourceNotFoundException("Course not found: " + req.getCourseId()));

            CourseEnrollmentId enrollmentId = new CourseEnrollmentId(student.getId(), course.getId(), semester.getId());

            CourseEnrollments enrollment = new CourseEnrollments();
            enrollment.setId(enrollmentId);
            enrollment.setStudent(student);
            enrollment.setCourse(course);
            enrollment.setSemester(semester);
            enrollment.setEnrollmentDate(req.getEnrollmentDate() != null
                    ? req.getEnrollmentDate()
                    : java.time.LocalDate.now());
            enrollment.setStatus("active");
            enrollment.setCreatedAt(java.time.LocalDateTime.now());

            courseEnrollmentRepository.save(enrollment);
        }
    }

    @Override
    public void DeleteCourse(DeleteCourse courseInfo) {
        Students student = studentRepository.findById(courseInfo.getStudentId())
                .orElseThrow(() -> new StudentNotFoundException("Student not found under that ID"));

        Semesters semesters = semestersRepository.findById(courseInfo.getSemesterId())
                .orElseThrow(() -> new ResourceNotFoundException("A semester by that Id does not exists"));

        Courses course = courseRepository.findById(courseInfo.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("This course does not exist"));

        // Get composite primary key for courseEnrollment
        CourseEnrollmentId courseEnrollmentId = new CourseEnrollmentId(student.getId(), course.getId(), semesters.getId());

        CourseEnrollments courseEnrollments = courseEnrollmentRepository.findById(courseEnrollmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment does not exist"));

        courseEnrollments.setStatus("dropped"); // Dropped the course
        courseEnrollments.setGrade("W"); // Withdrawn
    }

    // Helper methods for validation and mock data
    private void validateCourseRegistration(CourseRegistrationRequest request, Students student, Semesters semester) {
        for (CourseRegistrationRequest.CourseEnrollmentRequest courseReq : request.getCourses()) {
            // Check if course exists
            Courses course = courseRepository.findById(courseReq.getCourseId())
                    .orElseThrow(() -> new RegistrationValidationException("Course not found: " + courseReq.getCourseId()));
            
            // Check if already enrolled
            CourseEnrollmentId enrollmentId = new CourseEnrollmentId(student.getId(), course.getId(), semester.getId());
            if (courseEnrollmentRepository.findById(enrollmentId).isPresent()) {
                throw new AlreadyEnrolledException("Already enrolled in course: " + courseReq.getCourseId());
            }
            
            // Check prerequisites (mock validation)
            if (courseReq.getCourseId().equals("CS201") && !hasPrerequisite(student, "CS101")) {
                throw new RegistrationValidationException("Prerequisite CS101 not completed for course CS201");
            }
            
            // Check capacity (mock validation)
            if (courseReq.getCourseId().equals("MATH301") && getEnrollmentCount(course) >= 25) {
                throw new RegistrationValidationException("Course capacity reached for MATH301");
            }
        }
        
        // Check for schedule conflicts (mock validation)
        if (request.getCourses().size() > 1) {
            throw new ScheduleConflictException("Schedule conflict detected between selected courses");
        }
    }

    private CourseRegistrationDTO createRegistrationResponse(CourseRegistrationRequest request, Students student, Semesters semester) {
        List<CourseRegistrationDTO.EnrollmentDetailDTO> enrollments = request.getCourses().stream()
                .map(courseReq -> {
                    Courses course = courseRepository.findById(courseReq.getCourseId())
                            .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
                    
                    return new CourseRegistrationDTO.EnrollmentDetailDTO(
                            courseReq.getCourseId(),
                            course.getName(),
                            course.getCode(),
                            courseReq.getEnrollmentDate(),
                            "active",
                            new BigDecimal("1500.00") // Mock fee
                    );
                }).collect(Collectors.toList());
        
        BigDecimal totalFee = enrollments.stream()
                .map(CourseRegistrationDTO.EnrollmentDetailDTO::getFee)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        CourseRegistrationDTO.FeeBreakdownDTO feeBreakdown = new CourseRegistrationDTO.FeeBreakdownDTO(
                totalFee,
                new BigDecimal("100.00"),
                totalFee.add(new BigDecimal("100.00"))
        );
        
        CourseRegistrationDTO.EnrollmentConfirmationDTO confirmation = new CourseRegistrationDTO.EnrollmentConfirmationDTO(
                "CONF" + UUID.randomUUID().toString().substring(0, 8),
                LocalDateTime.now(),
                "confirmed"
        );
        
        return new CourseRegistrationDTO(
                "REG" + UUID.randomUUID().toString().substring(0, 8),
                student.getId(),
                semester.getId().intValue(),
                semester.getName(),
                enrollments,
                totalFee,
                feeBreakdown,
                LocalDate.now().plusMonths(1),
                confirmation
        );
    }

    // Mock data methods
    private List<AvailableCoursesResponseDTO.StudentEnrollmentDTO> getMockStudentEnrollments() {
        return Arrays.asList(
                new AvailableCoursesResponseDTO.StudentEnrollmentDTO("CS101", "active", "2024-09-01", null),
                new AvailableCoursesResponseDTO.StudentEnrollmentDTO("MATH201", "active", "2024-09-01", null)
        );
    }

    private List<AvailableCourseDTO> getMockAvailableCourses() {
        return Arrays.asList(
                createMockAvailableCourse("CS201", "Data Structures and Algorithms", "CS201", 3, 
                        "Advanced programming concepts and algorithm analysis", "LEC004", "Dr. Robert Wilson", 
                        "robert.wilson@university.edu", 30, 25, 1500.00, Arrays.asList("CS101"), 
                        Arrays.asList("Monday", "Wednesday"), "10:00 AM - 11:30 AM", "Computer Lab 101"),
                createMockAvailableCourse("MATH301", "Linear Algebra", "MATH301", 4, 
                        "Vector spaces, linear transformations, and eigenvalues", "LEC005", "Prof. Lisa Anderson", 
                        "lisa.anderson@university.edu", 25, 20, 1200.00, Arrays.asList("MATH201"), 
                        Arrays.asList("Tuesday", "Thursday"), "2:00 PM - 3:30 PM", "Mathematics Building 205")
        );
    }

    private AvailableCourseDTO createMockAvailableCourse(String id, String name, String code, Integer credits, 
                                                        String description, String lecturerId, String lecturerName, 
                                                        String lecturerEmail, Integer capacity, Integer enrolled, 
                                                        double fee, List<String> prerequisites, List<String> days, 
                                                        String time, String room) {
        AvailableCourseDTO course = new AvailableCourseDTO();
        course.setId(id);
        course.setName(name);
        course.setCode(code);
        course.setCredits(credits);
        course.setDescription(description);
        course.setLecturer(new LecturerDto(lecturerId, lecturerName, lecturerEmail));
        course.setCapacity(capacity);
        course.setEnrolled(enrolled);
        course.setSemesterId(1);
        course.setFee(new BigDecimal(fee));
        course.setPrerequisites(prerequisites);
        
        AvailableCourseDTO.CourseScheduleDTO schedule = new AvailableCourseDTO.CourseScheduleDTO();
        schedule.setDays(days);
        schedule.setTime(time);
        schedule.setRoom(room);
        course.setSchedule(schedule);
        
        return course;
    }

    private List<SemesterDTO.CurrentSemesterDTO> getMockSemesters() {
        return Arrays.asList(
                new SemesterDTO.CurrentSemesterDTO(1, "Fall 2024", "Fall", 2024, 
                        LocalDate.of(2024, 9, 1), LocalDate.of(2024, 12, 15), true),
                new SemesterDTO.CurrentSemesterDTO(2, "Spring 2025", "Spring", 2025, 
                        LocalDate.of(2025, 1, 15), LocalDate.of(2025, 5, 1), true),
                new SemesterDTO.CurrentSemesterDTO(3, "Summer 2025", "Summer", 2025, 
                        LocalDate.of(2025, 6, 1), LocalDate.of(2025, 8, 15), false)
        );
    }

    private Object createMockFeeCalculation(String studentId, Integer semesterId, String courseIds) {
        FeeCalculationDTO calculation = new FeeCalculationDTO();
        calculation.setStudentId(studentId);
        calculation.setSemesterId(semesterId);
        calculation.setSemesterName("Fall 2024");
        
        // Mock course fees
        List<FeeCalculationDTO.CourseFeeDTO> courseFees = Arrays.asList(
                new FeeCalculationDTO.CourseFeeDTO("CS201", "Data Structures and Algorithms", "CS201", 3, new BigDecimal("1500.00")),
                new FeeCalculationDTO.CourseFeeDTO("MATH301", "Linear Algebra", "MATH301", 4, new BigDecimal("1200.00"))
        );
        calculation.setCourseFees(courseFees);
        
        // Mock fee breakdown
        FeeCalculationDTO.FeeBreakdownDTO feeBreakdown = new FeeCalculationDTO.FeeBreakdownDTO(
                new BigDecimal("2700.00"),
                new BigDecimal("100.00"),
                new BigDecimal("50.00"),
                new BigDecimal("2850.00")
        );
        calculation.setFeeBreakdown(feeBreakdown);
        
        // Mock payment options
        FeeCalculationDTO.PaymentOptionsDTO paymentOptions = new FeeCalculationDTO.PaymentOptionsDTO();
        paymentOptions.setFullPayment(new FeeCalculationDTO.FullPaymentDTO(
                new BigDecimal("2850.00"),
                LocalDate.of(2024, 2, 15),
                BigDecimal.ZERO
        ));
        paymentOptions.setInstallmentPlan(new FeeCalculationDTO.InstallmentPlanDTO(
                new BigDecimal("2850.00"),
                3,
                new BigDecimal("950.00"),
                Arrays.asList(LocalDate.of(2024, 2, 15), LocalDate.of(2024, 3, 15), LocalDate.of(2024, 4, 15))
        ));
        calculation.setPaymentOptions(paymentOptions);
        
        return calculation;
    }

    // Helper methods for validation
    private boolean hasPrerequisite(Students student, String prerequisiteCourseId) {
        // Mock implementation - in real app, check student's completed courses
        return true;
    }

    private int getEnrollmentCount(Courses course) {
        // Mock implementation - in real app, count actual enrollments
        return 20;
    }
}
