package com.example.demo.dtos;

import lombok.Data;

@Data
public class CourseDto {
    private String id;
    private String name;
    private String code;
    private LecturerDto lecturers;
}
