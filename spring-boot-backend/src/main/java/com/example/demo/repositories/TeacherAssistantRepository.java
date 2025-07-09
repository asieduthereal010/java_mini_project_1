package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.TeacherAssistants;

/**
 * TeacherAssistantRepository
 */
public interface TeacherAssistantRepository extends JpaRepository<TeacherAssistants, String> {

  
}
