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
public class FeeBreakdownDTO {
    private String studentId;
    private String studentName;
    private BigDecimal totalAmount;
    private BigDecimal amountPaid;
    private BigDecimal amountOwed;
    private LocalDate dueDate;
    private List<PaymentHistoryDTO.PaymentDetailDTO> paymentHistory;
    private List<FeeCategoryDTO> feeBreakdown;
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FeeCategoryDTO {
        private String category;
        private BigDecimal amount;
        private BigDecimal paid;
        private BigDecimal remaining;
    }
} 