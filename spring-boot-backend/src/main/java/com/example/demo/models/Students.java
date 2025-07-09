package com.example.demo.models;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import java.util.List;
import java.util.Set;

@Entity
public class  Students{
  @Id
  private String id; 
  private String name; 
  private Date date_of_birth; 
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
}
