package com.example.demo.services.user;

import com.example.demo.dtos.UserDto;

public interface IUserService {
    UserDto authenticateUser(String email, String password);
    Object login(String email, String password);
    UserDto saveUser(String username, String email, String password);
}
