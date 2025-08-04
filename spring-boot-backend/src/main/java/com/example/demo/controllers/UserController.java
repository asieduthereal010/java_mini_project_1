package com.example.demo.controllers;

import com.example.demo.dtos.StudentDto;
import com.example.demo.dtos.UserDto;
import com.example.demo.exceptions.*;
import com.example.demo.requests.user.LoginRequest;
import com.example.demo.requests.user.UserRequest;
import com.example.demo.response.ApiResponse;
import com.example.demo.services.user.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class UserController {
    private final IUserService userService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> getUser(@RequestBody LoginRequest request){
        try {
            StudentDto studentDto = userService.authenticateUser(request.getId(), request.getPassword());
            return ResponseEntity.ok(new ApiResponse("Found", studentDto));

        } catch (ResourceNotFoundException | IncorrectPasswordException e) {
            return ResponseEntity.status(BAD_REQUEST)
                    .body(new ApiResponse(e.getMessage(), "Bad Request",400));

        } catch (UserNotFoundException | StudentNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), "Not found", 404));
        } catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), "Internal Server Error", 500));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createUser(@RequestBody UserRequest request){
        try {
            StudentDto studentDto = userService.saveUser(
                    request.getId(), request.getPassword(),
                    request.getName(), request.getDate_of_birth(), request.getEmail()
            );
            return ResponseEntity.ok(new ApiResponse("Created", studentDto));

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(BAD_REQUEST)
                    .body(new ApiResponse(e.getMessage(), null));

        } catch (AlreadyExistsException e){
            return ResponseEntity.status(CONFLICT)
                    .body(new ApiResponse(e.getMessage(), null));

        } catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }
}
