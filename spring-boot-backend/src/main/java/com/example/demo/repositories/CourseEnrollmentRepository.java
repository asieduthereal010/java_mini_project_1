package com.example.demo.repositories;

import com.example.demo.models.CourseEnrollmentId;
import com.example.demo.models.CourseEnrollments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseEnrollmentRepository extends JpaRepository<CourseEnrollments, CourseEnrollmentId> {
    Optional<List<CourseEnrollments>> findAllByStudentId(String studentId);
    List<CourseEnrollments> findAllByCourseId(String courseId);
}
