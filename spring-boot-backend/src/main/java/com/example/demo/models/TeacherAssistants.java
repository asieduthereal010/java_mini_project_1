package com.example.demo.models;

import java.sql.Date;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;

@Entity
public class TeacherAssistants {
  @Id
  private String id; 
  private String name; 
  private Date date_of_birth; 
  private String email;

  @ManyToMany(mappedBy = "teacherAssistants")
  private Set<Courses> courses;
}
