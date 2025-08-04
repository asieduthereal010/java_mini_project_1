package com.example.demo.controllers;

import com.example.demo.dtos.StudentDashboardDTO;
import com.example.demo.dtos.StudentDto;
import com.example.demo.dtos.PaymentHistoryDTO;
import com.example.demo.requests.students.ProfileUpdateRequest;
import com.example.demo.response.ApiResponse;
import com.example.demo.services.student.IStudentsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/student")
@AllArgsConstructor
public class StudentController {
    private final IStudentsService studentsService;

    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse> getStudentDashboard(@RequestParam String studentId) {
        try {
            StudentDashboardDTO dashboardData = studentsService.getStudentDashboard(studentId);
            return ResponseEntity.ok(new ApiResponse("Student dashboard data retrieved successfully", dashboardData));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error retrieving student dashboard data: " + e.getMessage(), "DASHBOARD_ERROR", 500));
        }
    }

    @GetMapping("/payments")
    public ResponseEntity<ApiResponse> getPaymentHistory(@RequestParam String studentId) {
        try {
            PaymentHistoryDTO paymentHistory = studentsService.getPaymentHistory(studentId);
            return ResponseEntity.ok(new ApiResponse("Payment history retrieved successfully", paymentHistory));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error retrieving payment history: " + e.getMessage(), "PAYMENT_HISTORY_ERROR", 500));
        }
    }

    @GetMapping("/fees/overview")
    public ResponseEntity<ApiResponse> getFeesOverview(@RequestParam String studentId) {
        try {
            Object feesOverview = studentsService.getFeesOverview(studentId);
            return ResponseEntity.ok(new ApiResponse("Fees overview retrieved successfully", feesOverview));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error retrieving fees overview: " + e.getMessage(), "FEES_OVERVIEW_ERROR", 500));
        }
    }

    @PutMapping("/profile/update")
    public ResponseEntity<ApiResponse> updateStudentProfile(@RequestBody ProfileUpdateRequest request) {
        try {
            StudentDto updatedStudent = studentsService.updateStudentDetails(request);
            return ResponseEntity.ok(new ApiResponse("Student profile updated successfully", updatedStudent));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error updating student profile: " + e.getMessage(), "PROFILE_UPDATE_ERROR", 500));
        }
    }
} 