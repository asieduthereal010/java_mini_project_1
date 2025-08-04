package com.example.demo.services.lecturer;

import com.example.demo.dtos.LecturerTADashboardDTO;
import com.example.demo.repositories.LecturerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LecturersService implements ILecturersService{
    private final LecturerRepository lecturerRepository;

    @Override
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