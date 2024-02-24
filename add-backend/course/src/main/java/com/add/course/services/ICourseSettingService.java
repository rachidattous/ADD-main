package com.add.course.services;

import com.add.course.dto.SettingDTO;
import com.add.course.model.Course;

import java.util.Optional;

public interface ICourseSettingService {

    Optional<Course> addSettings(String courseId,SettingDTO settingDTO);
}
