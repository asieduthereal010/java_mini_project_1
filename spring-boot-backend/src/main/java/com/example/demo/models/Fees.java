package com.example.demo.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Fees {
  @Id
  private Long id;
  @Column(precision = 10, scale = 2, nullable = false)
  private BigDecimal total_amount;
  @Column(precision = 10, scale = 2, nullable = false)
  private BigDecimal amount_paid;
  @Column(length = 9)
  private String academicYear;
  private LocalDate dueDate;
  @CreationTimestamp
  @Column(nullable = false, updatable = false)
  private LocalDate createdAt;
  @UpdateTimestamp
  private LocalDate updatedAt;

  @ManyToOne
  @JoinColumn(name = "student_id")
  private Students student;

  @ManyToOne
  @JoinColumn(name = "semester_id")
  private Semesters semester;

  @OneToMany
  private List<Payments> payments;
}
