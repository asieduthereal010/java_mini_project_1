package com.example.demo.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentDto {
    private String id;
    private String name;
    private LocalDate date_of_birth;
    private String email;
    private CourseDto course;
}
