package com.example.demo.requests.course_enrollments;

import lombok.Data;

@Data
public class DeleteCourse {
    private String studentId;
    private String courseId;
    private Long semesterId;
    private String reason;
}
