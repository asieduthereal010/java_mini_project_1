package com.example.demo.models;

import java.util.List;

public class StudentDashboardDTO {
    private String id;
    private String name;
    private List<String> courses;
    private Float feesOwed;
    private Float feesPaid;

    public StudentDashboardDTO(String id, String name, List<String> courses, Float feesOwed, Float feesPaid) {
        this.id = id;
        this.name = name;
        this.courses = courses;
        this.feesOwed = feesOwed;
        this.feesPaid = feesPaid;
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<String> getCourses() { return courses; }
    public void setCourses(List<String> courses) { this.courses = courses; }
    public Float getFeesOwed() { return feesOwed; }
    public void setFeesOwed(Float feesOwed) { this.feesOwed = feesOwed; }
    public Float getFeesPaid() { return feesPaid; }
    public void setFeesPaid(Float feesPaid) { this.feesPaid = feesPaid; }
} 