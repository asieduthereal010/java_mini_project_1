package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.models.Students;
import com.example.demo.models.StudentDashboardDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
/**
 * StudentRepository
 */
public interface StudentRepository extends JpaRepository<Students, String> {
    @Query(value = "SELECT s.id as id, s.name as name, array_agg(c.name) as courses, COALESCE(f.total_amount, 0) as feesOwed, COALESCE(f.amount_paid, 0) as feesPaid " +
            "FROM students s " +
            "LEFT JOIN course_enrollments ce ON s.id = ce.student_id " +
            "LEFT JOIN courses c ON ce.course_id = c.id " +
            "LEFT JOIN fees f ON s.id = f.student_id " +
            "GROUP BY s.id, s.name, f.total_amount, f.amount_paid", 
            nativeQuery = true)
    List<Object[]> fetchStudentDashboardData();
}
