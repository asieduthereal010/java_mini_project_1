package com.example.demo.models;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
public class Fees {
  @Id
  private Integer id; 
  private Float total_amount;
  private Float amount_paid;

  @ManyToOne
  @JoinColumn(name = "student_id")
  private Students student;
}
