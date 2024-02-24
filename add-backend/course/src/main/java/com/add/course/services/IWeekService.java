package com.add.course.services;

import com.add.course.dto.WeekDTO;
import com.add.course.model.Course;
import com.add.course.model.Week;

import java.util.List;
import java.util.Optional;

public interface IWeekService {

    Optional<Course> createWeek(String courseId, List<WeekDTO> weekDTOList);

    Optional<Week> getWeekById(String weekId);

    List<Week> getAll(String courseId);

    Optional<Week> updateWeek(String weekId, WeekDTO weekDTO);

    void deleteWeek(String weekId);
}
