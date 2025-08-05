package com.example.demo.repositories;

import com.example.demo.models.Semesters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SemestersRepository extends JpaRepository<Semesters, Long> {
    Optional<Semesters> findByAcademicYearAndSemesterNumber(String academicYear, Long semesterNumber);
}
