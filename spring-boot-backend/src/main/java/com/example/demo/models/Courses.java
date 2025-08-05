package com.example.demo.models;

import java.util.List;
import java.util.Set;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Courses {
  @Id
  private String id;
  private String name;
  private String code;

  @OneToMany(mappedBy = "course")
  private List<CourseEnrollments> enrollments;

  @ManyToMany
  @JoinTable(
    name = "course_lecturers",
    joinColumns = @JoinColumn(name = "course_id"),
    inverseJoinColumns = @JoinColumn(name = "lecturer_id")
  )
  private Set<Lecturers> lecturers;

  @ManyToOne
  @JoinColumn(name="semester_id")
  private Semesters semester;

  public Courses(String name, String code){
    this.name = name;
    this.code = code;
  }

}
