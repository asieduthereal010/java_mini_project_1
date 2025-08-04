package com.example.demo.dtos;

import com.example.demo.models.Fees;
import com.example.demo.models.Students;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentDto {
    private Students student;
    private Fees fee;
    private BigDecimal amount;
    private String paymentMethod;
    private LocalDateTime timestamp;
    private String transactionId;
    private String status;
    private String description;

}
