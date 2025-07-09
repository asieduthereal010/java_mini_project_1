package com.example.demo.models;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;

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
}
