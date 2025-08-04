package com.example.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeeCalculationDTO {
    private String studentId;
    private Integer semesterId;
    private String semesterName;
    private List<CourseFeeDTO> courseFees;
    private FeeBreakdownDTO feeBreakdown;
    private PaymentOptionsDTO paymentOptions;
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CourseFeeDTO {
        private String courseId;
        private String courseName;
        private String courseCode;
        private Integer credits;
        private BigDecimal fee;
    }
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FeeBreakdownDTO {
        private BigDecimal courseFees;
        private BigDecimal registrationFee;
        private BigDecimal technologyFee;
        private BigDecimal totalAmount;
    }
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PaymentOptionsDTO {
        private FullPaymentDTO fullPayment;
        private InstallmentPlanDTO installmentPlan;
    }
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FullPaymentDTO {
        private BigDecimal amount;
        private LocalDate dueDate;
        private BigDecimal discount;
    }
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class InstallmentPlanDTO {
        private BigDecimal amount;
        private Integer installments;
        private BigDecimal amountPerInstallment;
        private List<LocalDate> dueDates;
    }
} 