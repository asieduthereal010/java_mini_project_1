package com.example.demo.requests.courses;

import lombok.Data;

@Data
public class CreateCourseRequest {
    private String name;
    private String code;
}
