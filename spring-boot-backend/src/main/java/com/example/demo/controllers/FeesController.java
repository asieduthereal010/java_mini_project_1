package com.example.demo.controllers;

import com.example.demo.models.Fees;
import com.example.demo.requests.fees.FeeInquiryRequest;
import com.example.demo.response.ApiResponse;
import com.example.demo.services.fees.IFeesService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/fees")
@AllArgsConstructor
public class FeesController {
    private final IFeesService feesService;

    @PostMapping("/inquiry")
    public ResponseEntity<ApiResponse> getFeesDetail(@RequestBody FeeInquiryRequest request) {
        try {
            Fees feesDetail = feesService.getFeesDetail(request);
            return ResponseEntity.ok(new ApiResponse("Fees details retrieved successfully", feesDetail));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error retrieving fees details: " + e.getMessage(), null));
        }
    }
} 