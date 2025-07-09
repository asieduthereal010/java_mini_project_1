package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.StudentDashboardDTO;
import com.example.demo.repositories.StudentRepository;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentsService {
  @Autowired
  StudentRepository studentRepository;

  public List<StudentDashboardDTO> getStudentDashboardData() {
    List<Object[]> results = studentRepository.fetchStudentDashboardData();
    return results.stream().map(row -> new StudentDashboardDTO(
      (String) row[0],
      (String) row[1],
      row[2] != null ? Arrays.asList((String[]) row[2]) : List.of(),
      row[3] != null ? ((Number) row[3]).floatValue() : 0f,
      row[4] != null ? ((Number) row[4]).floatValue() : 0f
    )).collect(Collectors.toList());
  }
}
