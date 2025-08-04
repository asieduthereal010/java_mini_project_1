package com.example.demo.repositories;

import com.example.demo.models.CourseEnrollmentId;
import com.example.demo.models.CourseEnrollments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseEnrollmentRepository extends JpaRepository<CourseEnrollments, CourseEnrollmentId> {
}
