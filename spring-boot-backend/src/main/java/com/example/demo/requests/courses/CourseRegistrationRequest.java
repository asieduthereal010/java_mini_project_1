package com.example.demo.requests.courses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseRegistrationRequest {
    private String studentId;
    private Long semesterId;
    private List<CourseEnrollmentRequest> courses;
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CourseEnrollmentRequest {
        private String courseId;
        private LocalDate enrollmentDate;
    }
} 