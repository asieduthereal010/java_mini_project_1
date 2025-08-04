package com.example.demo.requests.lecturers;

import lombok.Data;

@Data
public class ContactLecturerRequest {
    private String studentId;
    private String lecturerId;
    private String subject;
    private String message;
    private String courseId;
}
