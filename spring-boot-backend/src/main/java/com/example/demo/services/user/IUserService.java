package com.example.demo.services.user;

import com.example.demo.dtos.StudentDto;

import java.time.LocalDate;


public interface IUserService {
    StudentDto authenticateUser(String id, String password);
    StudentDto saveUser(String id, String password, String name, LocalDate dob, String email);
}
