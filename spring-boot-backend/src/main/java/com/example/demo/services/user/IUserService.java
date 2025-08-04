package com.example.demo.services.user;

import com.example.demo.dtos.UserDto;

public interface IUserService {
    UserDto authenticateUser(String id, String password);
    UserDto saveUser(String id, String password);
}
