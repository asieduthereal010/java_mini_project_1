package com.example.demo.services.courses;

import com.example.demo.dtos.CourseDto;
import com.example.demo.requests.course_enrollments.DeleteCourse;
import com.example.demo.requests.courses.CourseRegistrationRequest;
import com.example.demo.requests.courses.CreateCourseRequest;

public interface ICourseService {
    Object getAvailableCourses(String studentId);
    Object registerCourses(CourseRegistrationRequest request);
    Object calculateFees(String studentId, Integer semesterId, String courseIds);
    void DeleteCourse(DeleteCourse courseInfo);
    CourseDto createCourse(CreateCourseRequest request);
}
