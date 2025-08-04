package com.example.demo.dtos;

import java.util.List;

public class LecturerTADashboardDTO {
    private String id;
    private String lecturer;
    private List<String> teachingAssistants;
    private List<String> courses;

    public LecturerTADashboardDTO(String id, String lecturer, List<String> teachingAssistants, List<String> courses) {
        this.id = id;
        this.lecturer = lecturer;
        this.teachingAssistants = teachingAssistants;
        this.courses = courses;
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getLecturer() { return lecturer; }
    public void setLecturer(String lecturer) { this.lecturer = lecturer; }
    public List<String> getTeachingAssistants() { return teachingAssistants; }
    public void setTeachingAssistants(List<String> teachingAssistants) { this.teachingAssistants = teachingAssistants; }
    public List<String> getCourses() { return courses; }
    public void setCourses(List<String> courses) { this.courses = courses; }
} 