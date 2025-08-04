package com.example.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseEnrollmentDTO {
    private String id;
    private String name;
    private String code;
    private LecturerDto lecturer;
    private Integer progress;
    private String semester;
    private String status;
    private String grade;
} 