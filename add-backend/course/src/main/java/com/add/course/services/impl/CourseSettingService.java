package com.add.course.services.impl;

import com.add.course.constants.Category;
import com.add.course.constants.Language;
import com.add.course.dto.SettingDTO;
import com.add.course.exception.ApiException;
import com.add.course.model.Course;
import com.add.course.model.CourseSetting;
import com.add.course.repository.CourseRepository;
import com.add.course.repository.CourseSettingRepository;
import com.add.course.services.ICourseSettingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CourseSettingService implements ICourseSettingService {

    private final CourseRepository courseRepository;

    private final CourseSettingRepository courseSettingRepository;



    @Override
    public Optional<Course> addSettings(String courseId, SettingDTO settingDTO) {
        return Optional.of(courseId)
                .map(c -> courseRepository.findById(c))
                .filter(c -> c.isPresent())
                .map(c -> c.get())
                .map(c -> {
                    CourseSetting courseSetting = CourseSetting.builder()
                            .category(Category.getByValue(settingDTO.getCategory()))
                            .language(Language.getByValue(settingDTO.getLanguage()))
                            .requiredScore(settingDTO.getRequiredScore())
                            .durationHour(settingDTO.getDurationHour())
                            .durationMinute(settingDTO.getDurationMinute())
                            .bronzeMin(settingDTO.getBronzeMin())
                            .bronzeMax(settingDTO.getBronzeMax())
                            .silverMin(settingDTO.getSilverMin())
                            .silverMax(settingDTO.getSilverMax())
                            .goldMin(settingDTO.getGoldMin())
                            .goldMax(settingDTO.getGoldMax())
                            .build();

                    courseSettingRepository.save(courseSetting);

                    c.setCourseSetting(courseSetting);

                    return Optional.of(courseRepository.save(c));
                })
                .orElseThrow(() -> new ApiException("Course with id : "+ courseId +" not found"));
    }
}
