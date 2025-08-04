package com.example.demo.requests.course_enrollments;

import lombok.Data;

import java.time.LocalDate;


@Data
public class RegisterCourses {
    private String studentId;
    private Long semesterId;
    private String courseId;
    private LocalDate enrollment;
}
