package com.example.demo.exceptions;

public class AlreadyEnrolledException extends RuntimeException {
    public AlreadyEnrolledException(String message) {
        super(message);
    }
    
    public AlreadyEnrolledException(String message, Throwable cause) {
        super(message, cause);
    }
} 