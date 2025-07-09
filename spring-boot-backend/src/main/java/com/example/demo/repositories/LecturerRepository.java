package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.Lecturers;
import com.example.demo.models.LecturerTADashboardDTO;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

/**
 * LecturerRepository
 */
public interface LecturerRepository extends JpaRepository<Lecturers, String> {

    @Query(value = "SELECT l.id as id, l.name as lecturer, array_agg(DISTINCT ta.name) as teachingAssistants, array_agg(DISTINCT c.name) as courses " +
            "FROM lecturers l " +
            "LEFT JOIN course_lecturers cl ON l.id = cl.lecturer_id " +
            "LEFT JOIN courses c ON cl.course_id = c.id " +
            "LEFT JOIN course_lecturer_assistants cla ON c.id = cla.course_id " +
            "LEFT JOIN teacher_assistants ta ON cla.lecturer_assistant_id = ta.id " +
            "GROUP BY l.id, l.name", 
            nativeQuery = true)
    List<Object[]> fetchLecturerTADashboardData();
}
