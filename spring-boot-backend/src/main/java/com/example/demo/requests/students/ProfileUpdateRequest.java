package com.example.demo.requests.students;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ProfileUpdateRequest {
    private String studentId;
    private String name;
    private String email;
    private LocalDate dateOfBirth;
}
