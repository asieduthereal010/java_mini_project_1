package com.example.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailableCoursesResponseDTO {
    private List<SemesterDTO.CurrentSemesterDTO> semesters;
    private List<AvailableCourseDTO> availableCourses;
    private List<StudentEnrollmentDTO> studentEnrollments;
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StudentEnrollmentDTO {
        private String courseId;
        private String status;
        private String enrollmentDate;
        private String grade;
    }
} 