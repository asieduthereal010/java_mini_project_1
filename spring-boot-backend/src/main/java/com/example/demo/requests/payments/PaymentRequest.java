package com.example.demo.requests.payments;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentRequest {
    private String studentId;
    private Long feeId;
    private BigDecimal amount;
    private String paymentMethod;
    private LocalDateTime timestamp;
    private String transactionId;
    private String status;
    private String description;
}
