package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.TeacherAssistants;
import org.springframework.stereotype.Repository;

/**
 * TeacherAssistantRepository
 */
@Repository
public interface TeacherAssistantRepository extends JpaRepository<TeacherAssistants, Long> {

  
}
