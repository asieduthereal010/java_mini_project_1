package com.example.demo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
    private Boolean success;
    private Object data;
    private String message;
    private String error;
    private String code;
    private Integer status;
    
    // Constructor for success responses
    public ApiResponse(String message, Object data) {
        this.success = true;
        this.message = message;
        this.data = data;
    }
    
    // Constructor for error responses
    public ApiResponse(String error, String code, Integer status) {
        this.success = false;
        this.error = error;
        this.code = code;
        this.status = status;
    }
    
    // Constructor for error responses with details
    public ApiResponse(String error, String code, Integer status, Object data) {
        this.success = false;
        this.error = error;
        this.code = code;
        this.status = status;
        this.data = data;
    }
}

