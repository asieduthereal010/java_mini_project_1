package com.example.demo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.demo.services.student.StudentsService;
import com.example.demo.services.lecturer.LecturersService;
import com.example.demo.dtos.StudentDashboardDTO;
import com.example.demo.dtos.LecturerTADashboardDTO;
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
