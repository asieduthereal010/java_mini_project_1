package com.example.demo.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "course_enrollments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseEnrollments {

    @EmbeddedId
    private CourseEnrollmentId id;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id", nullable = false)
    private Students student;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "course_id", nullable = false)
    private Courses course;

    @ManyToOne
    @MapsId("semesterId")
    @JoinColumn(name = "semester_id", nullable = false)
    private Semesters semester;

    @Column(name = "enrollment_date", columnDefinition = "DATE DEFAULT CURRENT_DATE")
    private LocalDate enrollmentDate = LocalDate.now();

    @Column(length = 20, columnDefinition = "VARCHAR(20) DEFAULT 'active'")
    private String status = "active";

    @Column(length = 2)
    private String grade;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt = LocalDateTime.now();
}