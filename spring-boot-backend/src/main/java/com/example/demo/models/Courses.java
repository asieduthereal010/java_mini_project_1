package com.example.demo.models;

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

  @ManyToMany(mappedBy = "courses")
  private Set<Students> students;

  @ManyToMany
  @JoinTable(
    name = "course_lecturers",
    joinColumns = @JoinColumn(name = "course_id"),
    inverseJoinColumns = @JoinColumn(name = "lecturer_id")
  )
  private Set<Lecturers> lecturers;

  @ManyToMany
  @JoinTable(
    name = "course_lecturer_assistants",
    joinColumns = @JoinColumn(name = "course_id"),
    inverseJoinColumns = @JoinColumn(name = "lecturer_assistant_id")
  )
  private Set<TeacherAssistants> teacherAssistants;

  @ManyToOne
  private Semesters semester;

  public Courses(String name, String code){
    this.name = name;
    this.code = code;
  }

}
