package com.example.demo.controllers;

import com.example.demo.dtos.UserDto;
import com.example.demo.exceptions.AlreadyExistsException;
import com.example.demo.exceptions.IncorrectPasswordException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.exceptions.UserNotFoundException;
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
    public ResponseEntity<ApiResponse> getUser(@RequestBody UserRequest request){
        try {
            UserDto userDto = userService.authenticateUser(request.getEmail(), request.getPassword());
            return ResponseEntity.ok(new ApiResponse("Found", userDto));
        } catch (ResourceNotFoundException | IncorrectPasswordException e) {
            return ResponseEntity.status(BAD_REQUEST)
                    .body(new ApiResponse(e.getMessage(), null));
        } catch (UserNotFoundException e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        } catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createUser(@RequestBody UserRequest request){
        try {
            UserDto userDto = userService.saveUser(request.getUsername(), request.getEmail(), request.getPassword());
            return ResponseEntity.ok(new ApiResponse("Created", userDto));
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
