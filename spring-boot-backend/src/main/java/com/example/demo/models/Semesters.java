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
    private int semesterNumber;
    @Column(length = 20)
    private String semesterType;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isActive = true;
    @Column(length = 9)
    private String academicYear;

    @OneToMany(mappedBy = "semester")
    private List<Fees> fees;

    @OneToMany(mappedBy = "semester")
    private List<Courses> courses;

    public Semesters(String name, int year, String semesterType, LocalDate startDate, LocalDate endDate, int semesterNumber) {
        this.name = name;
        this.year = year;
        this.semesterNumber = semesterNumber;
        this.semesterType = semesterType;
        this.startDate = startDate;
        this.endDate = endDate;

    }
}
