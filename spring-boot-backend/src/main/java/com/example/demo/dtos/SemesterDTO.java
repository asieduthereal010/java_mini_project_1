package com.example.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SemesterDTO {
    private CurrentSemesterDTO current;
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CurrentSemesterDTO {
        private Integer id;
        private String name;
        private String type;
        private Integer year;
        private LocalDate startDate;
        private LocalDate endDate;
        private Boolean isActive;
    }
} 