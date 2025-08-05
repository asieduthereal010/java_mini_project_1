package com.example.demo.services.fees;

import com.example.demo.dtos.FeesDTO;
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
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class FeesService implements IFeesService{
    private final FeeRepository feeRepository;
    private final StudentRepository studentRepository;
    private final SemestersRepository semestersRepository;
    private final ModelMapper modelmapper;

    @Override
    public FeesDTO getFeesDetail(FeeInquiryRequest request) {
        Students students = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new StudentNotFoundException("No student by that Id was found"));

        Semesters semester = semestersRepository.findByAcademicYearAndSemesterNumber(request.getAcademicYear(), request.getSemesterNumber())
                .orElseThrow(() -> new ResourceNotFoundException("The semester was not found"));

        Fees fee = feeRepository.findByAcademicYearAndStudentIdAndSemesterId(request.getAcademicYear(), students.getId(), semester.getId())
                .orElseThrow(()-> new ResourceNotFoundException("This fee does not exist"));
        FeesDTO feesDTO = modelmapper.map(fee, FeesDTO.class);
        feesDTO.setAmountOwed(fee.getTotalAmount().subtract(fee.getAmountPaid()));
        return feesDTO;
//        return getMockFees();
    }

    private FeesDTO getMockFees() {
        return new FeesDTO(
                1L,
                new BigDecimal("5500.00"),
                new BigDecimal("3000.00"),
                new BigDecimal("2500.00"),
                LocalDate.of(2024, 12, 31),
                "2024-2025",
                "Fall 2024"
        );
    }
}
