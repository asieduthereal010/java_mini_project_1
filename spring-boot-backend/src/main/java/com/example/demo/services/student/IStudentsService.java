package com.example.demo.services.student;

import com.example.demo.dtos.StudentDashboardDTO;
import com.example.demo.dtos.StudentDto;
import com.example.demo.dtos.PaymentHistoryDTO;
import com.example.demo.requests.students.ProfileUpdateRequest;

import java.util.List;

public interface IStudentsService {
    public List<StudentDashboardDTO> getStudentDashboardData();
    public StudentDashboardDTO getStudentDashboard(String studentId);
    public PaymentHistoryDTO getPaymentHistory(String studentId);
    public Object getFeesOverview(String studentId);
    public StudentDto updateStudentDetails(ProfileUpdateRequest request);
    public StudentDto getStudentDetailsById(String studentId);
    public List<StudentDto> getAllStudents();
}
