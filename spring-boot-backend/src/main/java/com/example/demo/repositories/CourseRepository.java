package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.Courses;

public interface CourseRepository extends JpaRepository<Courses, String> {

}
