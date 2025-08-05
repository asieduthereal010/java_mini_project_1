package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.Fees;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeeRepository extends JpaRepository<Fees, Long> {
    Optional<Fees> findByAcademicYearAndStudentIdAndSemesterId(String academicYear, String studentId, Long SemesterId);

    Optional<List<Fees>> findAllByStudentId(String studentId);
}
