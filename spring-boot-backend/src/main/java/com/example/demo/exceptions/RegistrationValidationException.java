package com.example.demo.exceptions;

public class RegistrationValidationException extends RuntimeException {
    public RegistrationValidationException(String message) {
        super(message);
    }
    
    public RegistrationValidationException(String message, Throwable cause) {
        super(message, cause);
    }
} 