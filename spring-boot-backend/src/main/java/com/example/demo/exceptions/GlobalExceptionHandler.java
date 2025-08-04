package com.example.demo.exceptions;

import com.example.demo.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ApiResponse> handleStudentNotFoundException(StudentNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse("Student not found", "STUDENT_NOT_FOUND", 404));
    }

    @ExceptionHandler(RegistrationValidationException.class)
    public ResponseEntity<ApiResponse> handleRegistrationValidationException(RegistrationValidationException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiResponse("Registration validation failed", "REGISTRATION_VALIDATION_ERROR", 409, e.getMessage()));
    }

    @ExceptionHandler(ScheduleConflictException.class)
    public ResponseEntity<ApiResponse> handleScheduleConflictException(ScheduleConflictException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiResponse("Course enrollment conflicts detected", "SCHEDULE_CONFLICT", 409, e.getMessage()));
    }

    @ExceptionHandler(AlreadyEnrolledException.class)
    public ResponseEntity<ApiResponse> handleAlreadyEnrolledException(AlreadyEnrolledException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiResponse("Already enrolled in selected courses", "ALREADY_ENROLLED", 409, e.getMessage()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleResourceNotFoundException(ResourceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse("Resource not found", "RESOURCE_NOT_FOUND", 404));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGenericException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse("Internal server error", "INTERNAL_ERROR", 500, e.getMessage()));
    }
} 