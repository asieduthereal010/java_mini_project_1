package com.example.demo.controllers;

import com.example.demo.dtos.LecturerTADashboardDTO;
import com.example.demo.response.ApiResponse;
import com.example.demo.services.lecturer.ILecturersService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/lecturers")
@AllArgsConstructor
public class LecturerController {
    private final ILecturersService lecturersService;

    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse> getLecturerTADashboardData() {
        try {
            List<LecturerTADashboardDTO> dashboardData = lecturersService.getLecturerTADashboardData();
            return ResponseEntity.ok(new ApiResponse("Lecturer/TA dashboard data retrieved successfully", dashboardData));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error retrieving lecturer/TA dashboard data: " + e.getMessage(), null));
        }
    }
} 