package com.example.demo.requests.course_enrollments;

import lombok.Data;

import java.util.List;

@Data
public class WebRegisterCourses {
    private String studentId;
    private Long semesterId;
    private List<RegisterCourses> courses;
}
