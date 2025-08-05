package com.example.demo.repositories;

import com.example.demo.models.Payments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentsRepository extends JpaRepository<Payments, String> {
    List<Payments> findAllByStudentId(String studentId);
}
