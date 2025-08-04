package com.example.demo.requests.user;

import lombok.Data;

@Data
public class LoginRequest {
    private String id;
    private String password;
}
