package com.example.demo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.demo.services.student.StudentsService;
import com.example.demo.services.lecturer.LecturersService;
import com.example.demo.dtos.StudentDashboardDTO;
import com.example.demo.dtos.LecturerTADashboardDTO;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.example.demo.services.StudentsService;
import com.example.demo.services.LecturersService;
import com.example.demo.models.StudentDashboardDTO;
import com.example.demo.models.LecturerTADashboardDTO;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/")
@CrossOrigin(originPatterns = "*", maxAge = 3600) // Use originPatterns instead of origins when allowCredentials is true
public class  RestApiController{

    private static final Logger logger = LoggerFactory.getLogger(RestApiController.class);

    @Autowired
    private StudentsService studentsService;
    @Autowired
    private LecturersService lecturersService;

    @GetMapping("/dashboard/students")
    public List<StudentDashboardDTO> getStudentDashboard() {
        logger.info("📊 Student dashboard requested");
        logger.debug("Fetching student dashboard data...");
        List<StudentDashboardDTO> result = studentsService.getStudentDashboardData();
        logger.info("✅ Student dashboard data retrieved successfully. Found {} students", result.size());
        return result;
    }

    @GetMapping("/dashboard/lecturers")
    public List<LecturerTADashboardDTO> getLecturerTADashboard() {
        logger.info("👨‍🏫 Lecturer/TA dashboard requested");
        logger.debug("Fetching lecturer dashboard data...");
        List<LecturerTADashboardDTO> result = lecturersService.getLecturerTADashboardData();
        logger.info("✅ Lecturer dashboard data retrieved successfully. Found {} lecturers/TAs", result.size());
        return result;
    }

    @GetMapping("/cors-test")
    public String corsTest() {
        logger.info("🧪 CORS test endpoint accessed");
        return "CORS is working! 🎉";
    }
}
