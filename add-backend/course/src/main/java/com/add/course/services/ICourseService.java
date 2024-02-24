package com.add.course.services;

import com.add.course.dto.CourseDTO;
import com.add.course.dto.SearchCourseDTO;
import com.add.course.dto.UpdateCourseDTO;
import com.add.course.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ICourseService {
    Optional<Course> createCourse(CourseDTO courseDTO);

    Page<Course> getCoursesByUserId(String userId, Pageable pageable);

    Page<Course> searchCourses(SearchCourseDTO searchCourseDTO, Pageable pageable);

    Optional<Course> updateCourse(String courseId, UpdateCourseDTO updateCourseDTO);

    void deleteCourse(String userId, String courseId);

}
