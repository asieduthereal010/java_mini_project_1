package com.example.demo.models;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@Embeddable
public class CourseEnrollmentId implements Serializable {
    private String studentId;
    private String courseId;
    private Long semesterId;

    // equals() and hashCode() are required
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CourseEnrollmentId that)) return false;
        return Objects.equals(studentId, that.studentId)
                && Objects.equals(courseId, that.courseId)
                && Objects.equals(semesterId, that.semesterId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, courseId, semesterId);
    }

}

