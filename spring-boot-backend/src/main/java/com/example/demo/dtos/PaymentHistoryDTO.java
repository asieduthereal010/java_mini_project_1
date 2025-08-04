package com.example.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentHistoryDTO {
    private List<PaymentDetailDTO> payments;
    private PaymentSummaryDTO summary;
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PaymentDetailDTO {
        private String id;
        private String studentId;
        private BigDecimal amount;
        private String paymentMethod;
        private LocalDateTime paymentDate;
        private String transactionId;
        private String status;
        private String description;
        private Integer feeId;
        private String semester;
    }
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PaymentSummaryDTO {
        private BigDecimal totalPaid;
        private Integer totalTransactions;
        private LocalDateTime lastPaymentDate;
    }
} 