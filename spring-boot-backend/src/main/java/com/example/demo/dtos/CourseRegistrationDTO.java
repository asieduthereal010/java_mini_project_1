package com.example.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseRegistrationDTO {
    private String registrationId;
    private String studentId;
    private Integer semesterId;
    private String semesterName;
    private List<EnrollmentDetailDTO> enrollments;
    private BigDecimal totalFee;
    private FeeBreakdownDTO feeBreakdown;
    private LocalDate paymentDueDate;
    private EnrollmentConfirmationDTO enrollmentConfirmation;
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EnrollmentDetailDTO {
        private String courseId;
        private String courseName;
        private String courseCode;
        private LocalDate enrollmentDate;
        private String status;
        private BigDecimal fee;
    }
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FeeBreakdownDTO {
        private BigDecimal courseFees;
        private BigDecimal registrationFee;
        private BigDecimal totalAmount;
    }
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EnrollmentConfirmationDTO {
        private String confirmationNumber;
        private LocalDateTime timestamp;
        private String status;
    }
} 