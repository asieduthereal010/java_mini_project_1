package com.example.demo.services;

import com.example.demo.models.LecturerTADashboardDTO;
import com.example.demo.repositories.LecturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LecturersService {
    @Autowired
    LecturerRepository lecturerRepository;

    public List<LecturerTADashboardDTO> getLecturerTADashboardData() {
        List<Object[]> results = lecturerRepository.fetchLecturerTADashboardData();
        return results.stream().map(row -> new LecturerTADashboardDTO(
                (String) row[0],
                (String) row[1],
                row[2] != null ? Arrays.asList((String[]) row[2]) : List.of(),
                row[3] != null ? Arrays.asList((String[]) row[3]) : List.of()
        )).collect(Collectors.toList());
    }
} 