package com.example.demo.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CurrentTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Payments {
    @Id
    private String transactionId;

    @Column(precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(length = 50)
    private String paymentMethod;

    @CurrentTimestamp
    private LocalDateTime paymentDate;

    @Column(length = 20)
    private String status = "completed";

    @CurrentTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDate createdAt;
    private boolean isActive = true;

    @ManyToOne
    private Students student;

    @ManyToOne
    private Fees fee;
}
