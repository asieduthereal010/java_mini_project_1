package com.example.demo.controllers;

import com.example.demo.dtos.PaymentDto;
import com.example.demo.requests.payments.PaymentRequest;
import com.example.demo.response.ApiResponse;
import com.example.demo.services.payments.IPaymentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/payments")
@AllArgsConstructor
public class PaymentController {
    private final IPaymentService paymentService;

    @PostMapping()
    public ResponseEntity<ApiResponse> makePayment(@RequestBody PaymentRequest request) {
        try {
            PaymentDto payment = paymentService.createPayment(request);
            return ResponseEntity.ok(new ApiResponse("Payment processed successfully", payment));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error processing payment: " + e.getMessage(), "PAYMENT_ERROR", 500));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createPayment(@RequestBody PaymentRequest request) {
        try {
            PaymentDto payment = paymentService.createPayment(request);
            return ResponseEntity.ok(new ApiResponse("Payment created successfully", payment));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error creating payment: " + e.getMessage(), "PAYMENT_CREATE_ERROR", 500));
        }
    }
} 