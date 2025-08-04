package com.example.demo.controllers;

import com.example.demo.dtos.CourseRegistrationDTO;
import com.example.demo.exceptions.StudentNotFoundException;
import com.example.demo.requests.course_enrollments.DeleteCourse;
import com.example.demo.requests.course_enrollments.WebRegisterCourses;
import com.example.demo.requests.courses.CourseRegistrationRequest;
import com.example.demo.response.ApiResponse;
import com.example.demo.services.courses.ICourseService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/courses")
@AllArgsConstructor
public class CourseController {
    private final ICourseService courseService;

    @GetMapping("/available")
    public ResponseEntity<ApiResponse> getAvailableCourses(
            @RequestParam String studentId)
             {
        try {
            Object availableCourses = courseService.getAvailableCourses(studentId);
            return ResponseEntity.ok(new ApiResponse("Available courses retrieved successfully", availableCourses));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error retrieving available courses: " + e.getMessage(), "COURSES_ERROR", 500));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerCourse(@RequestBody CourseRegistrationRequest request) {
        try {
            courseService.registerCourses(request);
            return ResponseEntity.ok(new ApiResponse("Course registration completed successfully", null));
        } catch (StudentNotFoundException e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse("Student not found", null));
        } catch (Exception e) {
            return ResponseEntity.status(CONFLICT)
                    .body(new ApiResponse("Registration validation failed", "REGISTRATION_VALIDATION_ERROR", 409, e.getMessage()));
        }
    }

    @GetMapping("/fees/calculate")
    public ResponseEntity<ApiResponse> calculateFees(
            @RequestParam String studentId,
            @RequestParam Integer semesterId,
            @RequestParam String courseIds) {
        try {
            Object feeCalculation = courseService.calculateFees(studentId, semesterId, courseIds);
            return ResponseEntity.ok(new ApiResponse("Fee calculation completed successfully", feeCalculation));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error calculating fees: " + e.getMessage(), "FEE_CALCULATION_ERROR", 500));
        }
    }


    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteCourse(@RequestBody DeleteCourse request) {
        try {
            courseService.DeleteCourse(request);
            return ResponseEntity.ok(new ApiResponse("Course deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error deleting course: " + e.getMessage(), "DELETE_ERROR", 500));
        }
    }

    @GetMapping()
    public ResponseEntity<ApiResponse> hello() {
        return ResponseEntity.ok(new ApiResponse("hello", "hi"));
    }
}
