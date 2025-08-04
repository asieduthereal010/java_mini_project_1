package com.example.demo.models;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Students{
  @Id
  private String id;
  private String name; 
  private LocalDate date_of_birth;
  private String email;

  /*below is for all the one-to-many relationships this table has with others*/
  @OneToMany(mappedBy = "student")
  private List<Fees> fees;

  @ManyToMany
  @JoinTable(
    name = "course_enrollments",
    joinColumns = @JoinColumn(name = "student_id"),
    inverseJoinColumns = @JoinColumn(name = "course_id")
  )
  private Set<Courses> courses;

  @OneToMany
  private List<Payments> payments;

  public Students(String name, LocalDate dob, String email){
    this.name = name;
    this.date_of_birth = dob;
    this.email = email;
  }
}
