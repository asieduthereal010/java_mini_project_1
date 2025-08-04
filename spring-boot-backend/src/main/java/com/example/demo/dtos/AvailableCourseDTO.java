package com.example.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailableCourseDTO {
    private String id;
    private String name;
    private String code;
    private Integer credits;
    private String description;
    private LecturerDto lecturer;
    private Integer capacity;
    private Integer enrolled;
    private Integer semesterId;
    private BigDecimal fee;
    private List<String> prerequisites;
    private CourseScheduleDTO schedule;
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CourseScheduleDTO {
        private List<String> days;
        private String time;
        private String room;
    }
} 