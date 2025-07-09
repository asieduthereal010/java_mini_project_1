package com.example.demo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.sql.Date;
import java.util.Set;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;

@Entity
public class Lecturers {
  @Id
  String id; 
  String name;
  private Date date_of_birth;
  String email;

  @ManyToMany(mappedBy = "lecturers")
  private Set<Courses> courses;
}
