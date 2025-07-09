package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.Fees;

public interface FeeRepository extends JpaRepository<Fees, Integer> {

  
}
