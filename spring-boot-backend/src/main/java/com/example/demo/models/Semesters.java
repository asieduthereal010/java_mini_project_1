package com.example.demo.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Semesters {
    @Id
    private Long id;
    @Column(length = 50)
    private String name;
    private int year;
    @Column(length = 20)
    private String semesterType;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isActive = true;

    @OneToMany
    private List<Fees> fees;

    @OneToMany
    private List<Courses> courses;

    public Semesters(String name, int year, String semesterType, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.year = year;
        this.semesterType = semesterType;
        this.startDate = startDate;
        this.endDate = endDate;

    }
}
