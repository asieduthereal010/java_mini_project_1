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
import com.example.demo.requests.courses.CreateCourseRequest;
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
import java.util.ArrayList;

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

        // Fetch real data from repositories
        AvailableCoursesResponseDTO response = new AvailableCoursesResponseDTO();
        
        // Get all semesters
        List<Semesters> allSemesters = semestersRepository.findAll();
        List<SemesterDTO.CurrentSemesterDTO> semesterDTOs = allSemesters.stream()
                .map(semester -> new SemesterDTO.CurrentSemesterDTO(
                        semester.getId().intValue(),
                        semester.getName(),
                        semester.getSemesterType(),
                        semester.getYear(),
                        semester.getStartDate(),
                        semester.getEndDate(),
                        semester.isActive()
                ))
                .collect(Collectors.toList());
        response.setSemesters(semesterDTOs);
        
        // Get all courses with their lecturers
        List<Courses> allCourses = courseRepository.findAll();
        List<AvailableCourseDTO> availableCourses = allCourses.stream()
                .map(this::convertToAvailableCourseDTO)
                .collect(Collectors.toList());
        response.setAvailableCourses(availableCourses);
        
        // Get student's current enrollments
        List<AvailableCoursesResponseDTO.StudentEnrollmentDTO> studentEnrollments = new ArrayList<>();
        try {
            List<CourseEnrollments> enrollments = courseEnrollmentRepository.findAllByStudentId(studentId)
                    .orElse(new ArrayList<>());
            studentEnrollments = enrollments.stream()
                    .map(enrollment -> new AvailableCoursesResponseDTO.StudentEnrollmentDTO(
                            enrollment.getCourse().getId(),
                            enrollment.getStatus(),
                            enrollment.getEnrollmentDate().toString(),
                            enrollment.getGrade()
                    ))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            // If no enrollments found, return empty list
        }
        response.setStudentEnrollments(studentEnrollments);
        
        return response;
    }

    private AvailableCourseDTO convertToAvailableCourseDTO(Courses course) {
        AvailableCourseDTO courseDTO = new AvailableCourseDTO();
        courseDTO.setId(course.getId());
        courseDTO.setName(course.getName());
        courseDTO.setCode(course.getCode());
        courseDTO.setCredits(3); // Default credits - you might want to add this field to the Courses model
        courseDTO.setDescription("Course description for " + course.getName()); // You might want to add this field to the Courses model
        
        // Get lecturer for this course
        if (course.getLecturers() != null && !course.getLecturers().isEmpty()) {
            Lecturers lecturer = course.getLecturers().iterator().next();
            courseDTO.setLecturer(new LecturerDto(lecturer.getId(), lecturer.getName(), lecturer.getEmail()));
        } else {
            courseDTO.setLecturer(new LecturerDto("N/A", "TBD", "tbd@university.edu"));
        }
        
        // Calculate enrollment count
        int enrolledCount = 0;
        try {
            List<CourseEnrollments> enrollments = courseEnrollmentRepository.findAllByCourseId(course.getId());
            enrolledCount = enrollments.size();
        } catch (Exception e) {
            // If error, set to 0
        }
        
        courseDTO.setCapacity(30); // Default capacity - you might want to add this field to the Courses model
        courseDTO.setEnrolled(enrolledCount);
        courseDTO.setSemesterId(course.getSemester() != null ? course.getSemester().getId().intValue() : 1);
        courseDTO.setFee(new BigDecimal("1500.00")); // Default fee - you might want to add this field to the Courses model
        courseDTO.setPrerequisites(new ArrayList<>()); // You might want to add this field to the Courses model
        
        // Create mock schedule - you might want to add this to the Courses model
        AvailableCourseDTO.CourseScheduleDTO schedule = new AvailableCourseDTO.CourseScheduleDTO();
        schedule.setDays(Arrays.asList("Monday", "Wednesday"));
        schedule.setTime("10:00 AM - 11:30 AM");
        schedule.setRoom("Room " + course.getCode());
        courseDTO.setSchedule(schedule);
        
        return courseDTO;
    }

    @Override
    public Object calculateFees(String studentId, Integer semesterId, String courseIds) {
        // Verify student exists
        studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Student not found"));
        
        // Verify semester exists
        Semesters semester = semestersRepository.findById(semesterId.longValue())
                .orElseThrow(() -> new ResourceNotFoundException("Semester not found"));
        
        // Parse course IDs
        List<String> courseIdList = Arrays.asList(courseIds.split(","));
        
        // Get courses from database
        List<Courses> courses = courseRepository.findAllById(courseIdList);
        
        // Create fee calculation response
        FeeCalculationDTO calculation = new FeeCalculationDTO();
        calculation.setStudentId(studentId);
        calculation.setSemesterId(semesterId);
        calculation.setSemesterName(semester.getName());
        
        // Calculate course fees
        List<FeeCalculationDTO.CourseFeeDTO> courseFees = courses.stream()
                .map(course -> new FeeCalculationDTO.CourseFeeDTO(
                        course.getId(),
                        course.getName(),
                        course.getCode(),
                        3, // Default credits - you might want to add this field to the Courses model
                        new BigDecimal("1500.00") // Default fee per course - you might want to add this field to the Courses model
                ))
                .collect(Collectors.toList());
        calculation.setCourseFees(courseFees);
        
        // Calculate total fees
        BigDecimal totalCourseFees = courseFees.stream()
                .map(FeeCalculationDTO.CourseFeeDTO::getFee)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal registrationFee = new BigDecimal("100.00");
        BigDecimal technologyFee = new BigDecimal("50.00");
        BigDecimal totalFees = totalCourseFees.add(registrationFee).add(technologyFee);
        
        // Create fee breakdown
        FeeCalculationDTO.FeeBreakdownDTO feeBreakdown = new FeeCalculationDTO.FeeBreakdownDTO(
                totalCourseFees,
                registrationFee,
                technologyFee,
                totalFees
        );
        calculation.setFeeBreakdown(feeBreakdown);
        
        // Create payment options
        FeeCalculationDTO.PaymentOptionsDTO paymentOptions = new FeeCalculationDTO.PaymentOptionsDTO();
        
        // Full payment option
        paymentOptions.setFullPayment(new FeeCalculationDTO.FullPaymentDTO(
                totalFees,
                LocalDate.now().plusMonths(1),
                BigDecimal.ZERO
        ));
        
        // Installment plan option
        int numberOfInstallments = 3;
        BigDecimal installmentAmount = totalFees.divide(new BigDecimal(numberOfInstallments), 2, BigDecimal.ROUND_HALF_UP);
        List<LocalDate> installmentDates = Arrays.asList(
                LocalDate.now().plusMonths(1),
                LocalDate.now().plusMonths(2),
                LocalDate.now().plusMonths(3)
        );
        
        paymentOptions.setInstallmentPlan(new FeeCalculationDTO.InstallmentPlanDTO(
                totalFees,
                numberOfInstallments,
                installmentAmount,
                installmentDates
        ));
        
        calculation.setPaymentOptions(paymentOptions);
        
        return calculation;
    }

    @Override
    public Object registerCourses(CourseRegistrationRequest request) {
        // Validate request
        if (request.getStudentId() == null || request.getStudentId().trim().isEmpty()) {
            throw new RegistrationValidationException("Student ID is required");
        }
        if (request.getSemesterId() == null) {
            throw new RegistrationValidationException("Semester ID is required");
        }
        if (request.getCourses() == null || request.getCourses().isEmpty()) {
            throw new RegistrationValidationException("At least one course must be selected for registration");
        }

        // Verify student exists
        Students student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new StudentNotFoundException("No student exists by that ID"));

        // Verify semester exists and is active
        Semesters semester = semestersRepository.findById(request.getSemesterId())
                .orElseThrow(() -> new ResourceNotFoundException("No semester exists by that ID"));

        if (!semester.isActive()) {
            throw new RegistrationValidationException("Cannot register for inactive semester: " + semester.getName());
        }

        // Validate all courses before processing
        validateCourseRegistration(request, student, semester);

        // Process course registrations
        List<CourseRegistrationDTO.EnrollmentDetailDTO> successfulEnrollments = new ArrayList<>();
        List<String> failedEnrollments = new ArrayList<>();

        for (CourseRegistrationRequest.CourseEnrollmentRequest courseReq : request.getCourses()) {
            try {
                Courses course = courseRepository.findById(courseReq.getCourseId())
                        .orElseThrow(() -> new ResourceNotFoundException("Course not found: " + courseReq.getCourseId()));

                // Check if already enrolled
            CourseEnrollmentId enrollmentId = new CourseEnrollmentId(student.getId(), course.getId(), semester.getId());
                if (courseEnrollmentRepository.findById(enrollmentId).isPresent()) {
                    failedEnrollments.add(courseReq.getCourseId() + " - Already enrolled");
                    continue;
                }

                // Create enrollment
            CourseEnrollments enrollment = new CourseEnrollments();
            enrollment.setId(enrollmentId);
            enrollment.setStudent(student);
            enrollment.setCourse(course);
            enrollment.setSemester(semester);
                enrollment.setEnrollmentDate(courseReq.getEnrollmentDate() != null
                        ? courseReq.getEnrollmentDate()
                        : LocalDate.now());
            enrollment.setStatus("active");
                enrollment.setCreatedAt(LocalDateTime.now());

                // Save enrollment
            courseEnrollmentRepository.save(enrollment);

                // Add to successful enrollments
                successfulEnrollments.add(new CourseRegistrationDTO.EnrollmentDetailDTO(
                        course.getId(),
                        course.getName(),
                        course.getCode(),
                        enrollment.getEnrollmentDate(),
                        enrollment.getStatus(),
                        new BigDecimal("1500.00") // Default fee per course
                ));

            } catch (Exception e) {
                failedEnrollments.add(courseReq.getCourseId() + " - " + e.getMessage());
            }
        }

        // Create response
        if (successfulEnrollments.isEmpty()) {
            throw new RegistrationValidationException("No courses were successfully registered. Errors: " + String.join(", ", failedEnrollments));
        }

        // Calculate fees
        BigDecimal totalFee = successfulEnrollments.stream()
                .map(CourseRegistrationDTO.EnrollmentDetailDTO::getFee)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal registrationFee = new BigDecimal("100.00");
        BigDecimal totalAmount = totalFee.add(registrationFee);

        // Create fee breakdown
        CourseRegistrationDTO.FeeBreakdownDTO feeBreakdown = new CourseRegistrationDTO.FeeBreakdownDTO(
                totalFee,
                registrationFee,
                totalAmount
        );

        // Create confirmation
        CourseRegistrationDTO.EnrollmentConfirmationDTO confirmation = new CourseRegistrationDTO.EnrollmentConfirmationDTO(
                "CONF" + UUID.randomUUID().toString().substring(0, 8),
                LocalDateTime.now(),
                "confirmed"
        );

        // Create response DTO
        CourseRegistrationDTO response = new CourseRegistrationDTO(
                "REG" + UUID.randomUUID().toString().substring(0, 8),
                student.getId(),
                semester.getId().intValue(),
                semester.getName(),
                successfulEnrollments,
                totalAmount,
                feeBreakdown,
                LocalDate.now().plusMonths(1), // Due date
                confirmation,
                failedEnrollments.isEmpty() ? null : failedEnrollments
        );

        // Add warnings if some enrollments failed
        if (!failedEnrollments.isEmpty()) {
            response.setWarnings(failedEnrollments);
        }
        

        return response;
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

    @Override
    public CourseDto createCourse(CreateCourseRequest request) {
        Courses course = new Courses();
        return null;
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
            
            // Check if course is offered in the selected semester
            if (course.getSemester() != null && !course.getSemester().getId().equals(semester.getId())) {
                throw new RegistrationValidationException("Course " + courseReq.getCourseId() + " is not offered in semester " + semester.getName());
            }
            
            // Check course capacity (using real enrollment count)
            int currentEnrollmentCount = courseEnrollmentRepository.findAllByCourseId(course.getId()).size();
            int maxCapacity = 30; // Default capacity - you might want to add this field to the Courses model
            if (currentEnrollmentCount >= maxCapacity) {
                throw new RegistrationValidationException("Course " + courseReq.getCourseId() + " has reached maximum capacity");
            }
        }
        
        // Check for duplicate course registrations in the same request
        List<String> courseIds = request.getCourses().stream()
                .map(CourseRegistrationRequest.CourseEnrollmentRequest::getCourseId)
                .collect(Collectors.toList());
        List<String> uniqueCourseIds = courseIds.stream().distinct().collect(Collectors.toList());
        if (courseIds.size() != uniqueCourseIds.size()) {
            throw new RegistrationValidationException("Duplicate course registrations are not allowed");
        }
        
        // Check maximum courses per semester (optional validation)
        int maxCoursesPerSemester = 6; // You might want to make this configurable
        if (request.getCourses().size() > maxCoursesPerSemester) {
            throw new RegistrationValidationException("Cannot register for more than " + maxCoursesPerSemester + " courses per semester");
        }
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
}


