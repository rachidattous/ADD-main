package com.add.course.repository;

import com.add.course.model.CourseSetting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseSettingRepository extends JpaRepository<CourseSetting, String> {
}
