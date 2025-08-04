package com.example.demo.requests.user;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserRequest {
    private String id;
    private String password;
    private String name;
    private String email;
    private LocalDate date_of_birth;
}
