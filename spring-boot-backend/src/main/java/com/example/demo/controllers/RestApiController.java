package com.example.demo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.demo.services.StudentsService;
import com.example.demo.services.LecturersService;
import com.example.demo.models.StudentDashboardDTO;
import com.example.demo.models.LecturerTADashboardDTO;
import java.util.List;

@RestController
@RequestMapping("/")
public class  RestApiController{

    @Autowired
    private StudentsService studentsService;
    @Autowired
    private LecturersService lecturersService;

    @GetMapping("/dashboard/students")
    public List<StudentDashboardDTO> getStudentDashboard() {
        return studentsService.getStudentDashboardData();
    }

    @GetMapping("/dashboard/lecturers")
    public List<LecturerTADashboardDTO> getLecturerTADashboard() {
        return lecturersService.getLecturerTADashboardData();
    }
}
