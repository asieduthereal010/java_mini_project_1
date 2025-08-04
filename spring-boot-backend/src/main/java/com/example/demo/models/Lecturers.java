package com.example.demo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Set;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Lecturers {
  @Id
  private String id;
  private String name;
  private LocalDate date_of_birth;
  private String email;

  @ManyToMany(mappedBy = "lecturers")
  private Set<Courses> courses;

  public Lecturers(String name, LocalDate dob, String email){
    this.name = name;
    this.date_of_birth = dob;
    this.email = email;
  }
}
