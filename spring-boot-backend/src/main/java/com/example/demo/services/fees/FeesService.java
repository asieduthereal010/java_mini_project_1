package com.example.demo.services.fees;

import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.exceptions.StudentNotFoundException;
import com.example.demo.models.Fees;
import com.example.demo.models.Semesters;
import com.example.demo.models.Students;
import com.example.demo.repositories.FeeRepository;
import com.example.demo.repositories.SemestersRepository;
import com.example.demo.repositories.StudentRepository;
import com.example.demo.requests.fees.FeeInquiryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeesService implements IFeesService{
    private final FeeRepository feeRepository;
    private final StudentRepository studentRepository;
    private final SemestersRepository semestersRepository;

    @Override
    public Fees getFeesDetail(FeeInquiryRequest request) {
        Students students = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new StudentNotFoundException("No student by that Id was found"));

        Semesters semester = semestersRepository.findById(request.getSemesterId())
                .orElseThrow(() -> new ResourceNotFoundException("The semester with this Id was not found"));

        return feeRepository.findByAcademicYearAndStudentIdAndSemesterId(request.getAcademicYear(), students.getId(), semester.getId())
                .orElseThrow(()-> new ResourceNotFoundException("This fee does not exist"));

    }
}
