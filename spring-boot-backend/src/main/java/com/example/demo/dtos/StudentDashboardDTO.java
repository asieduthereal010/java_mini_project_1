package com.example.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDashboardDTO {
    private String id;
    private String name;
    private String email;
    private LocalDate dateOfBirth;
    private List<CourseEnrollmentDTO> courses;
    private FeesDTO fees;
    private SemesterDTO semester;
} 