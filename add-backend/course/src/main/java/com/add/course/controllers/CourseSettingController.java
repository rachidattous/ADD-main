package com.add.course.controllers;

import com.add.course.dto.SettingDTO;
import com.add.course.model.Course;
import com.add.course.services.ICourseSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/course/setting")
public class CourseSettingController {

    private final ICourseSettingService iCourseSettingService;

    @PostMapping(value = "/{courseId}")
    public Optional<Course> addSettings(@PathVariable String courseId, @RequestBody SettingDTO settingDTO){
        return iCourseSettingService.addSettings(courseId, settingDTO);
    }
}
