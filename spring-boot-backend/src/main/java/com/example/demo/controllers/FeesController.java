package com.example.demo.controllers;

import com.example.demo.dtos.FeesDTO;
import com.example.demo.requests.fees.FeeInquiryRequest;
import com.example.demo.response.ApiResponse;
import com.example.demo.services.fees.IFeesService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/fees")
@AllArgsConstructor
public class FeesController {
  @Autowired
  private final IFeesService feesService;

    @GetMapping("/inquiry")
    public ResponseEntity<ApiResponse> getFeesDetail(@ModelAttribute FeeInquiryRequest request) {
        try {
            FeesDTO feesDetail = feesService.getFeesDetail(request);
            return ResponseEntity.ok(new ApiResponse("Fees details retrieved successfully", feesDetail));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error retrieving fees details: " + e.getMessage(), "Internal Server Error", 500));
        }
    }
} 
