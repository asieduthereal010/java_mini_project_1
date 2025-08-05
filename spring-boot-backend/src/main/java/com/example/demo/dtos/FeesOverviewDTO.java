package com.example.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeesOverviewDTO {
    private BigDecimal totalAmount;
    private BigDecimal amountPaid;
    private BigDecimal amountOwed;
    private LocalDate dueDate;
    private String academicYear;
    private String semester;
} 