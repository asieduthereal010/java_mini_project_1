package com.example.demo.repositories;

import com.example.demo.models.Semesters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SemestersRepository extends JpaRepository<Semesters, Long> {
}
