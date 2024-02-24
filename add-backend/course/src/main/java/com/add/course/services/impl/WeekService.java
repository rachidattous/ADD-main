package com.add.course.services.impl;

import com.add.course.dto.WeekDTO;
import com.add.course.exception.ApiException;
import com.add.course.model.Course;
import com.add.course.model.Week;
import com.add.course.repository.ActivityRepository;
import com.add.course.repository.CourseRepository;
import com.add.course.repository.WeekRepository;
import com.add.course.services.IWeekService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class WeekService implements IWeekService {

    private final WeekRepository weekRepository;

    private final CourseRepository courseRepository;

    private final ActivityRepository activityRepository;


    @Override
    public Optional<Course> createWeek(String courseId, List<WeekDTO> weekDTOList) {
        return Optional.of(courseId)
                .map(e -> courseRepository.findById(e))
                .filter(e -> e.isPresent())
                .map(e -> e.get())
                .map(e -> {
                    if (!weekDTOList.equals(null)){
                        weekDTOList.stream().forEach(week -> {
                            if(isValidWeekTitle(courseId, week.getTitle()) && isValidWeekNumber(courseId, week.getNumber())) {

                                Week newWeek = Week.builder()
                                        .number(week.getNumber())
                                        .title(week.getTitle())
                                        .build();

                                weekRepository.save(newWeek);
                                e.getWeeks().add(newWeek);

                                courseRepository.save(e);
                            } else
                                throw new ApiException("Weeks must have different titles");
                        });
                    } else
                        throw new ApiException("Weeks List is null");

                    e.setWeeks(e.getWeeks().stream()
                            .sorted(Comparator.comparingInt(Week::getNumber))
                            .collect(Collectors.toList()));
                    return Optional.of(e);
                }).orElseThrow(() -> new ApiException("Course with id: "+ courseId +" doesn't exist"));
    }

    @Override
    public Optional<Week> getWeekById(String weekId) {
        return Optional.of(weekId)
                .map(e -> weekRepository.findById(weekId))
                .filter(e -> e.isPresent())
                .map(e -> e.get())
                .map(e ->{
                    return Optional.of(e);
                })
                .orElseThrow(() -> new ApiException("Week With id: "+ weekId +" not found"));
    }

    @Override
    public List<Week> getAll(String courseId) {
        return Optional.of(courseId)
                .map(e -> courseRepository.findById(courseId))
                .filter(e -> e.isPresent())
                .map(e -> e.get())
                .map(e -> {
                    return e.getWeeks().stream()
                            .sorted(Comparator.comparingInt(Week::getNumber))
                            .collect(Collectors.toList());
                })
                .orElseThrow(() -> new ApiException("Course With id: "+ courseId +" not found"));
    }

    @Override
    public Optional<Week> updateWeek(String weekId ,WeekDTO weekDTO) {
        return Optional.of(weekId)
                .map(e -> weekRepository.findById(e))
                .filter(e -> e.isPresent())
                .map(e -> e.get())
                .map(e -> {

                    String courseId = courseRepository.findCourseByWeeks(e).get().getId();

                    if(isValidWeekNumber(courseId, weekDTO.getNumber()))
                        e.setNumber(weekDTO.getNumber());

                    if(isValidWeekTitle(courseId, weekDTO.getTitle()))
                        e.setTitle(weekDTO.getTitle());

                    return Optional.of(weekRepository.save(e));

                })
                .orElseThrow(() -> new ApiException("Week With id: "+ weekId +" not found"));
    }

    @Override
    public void deleteWeek(String weekId) {
        Optional.of(weekId)
                .map(w -> weekRepository.findById(w))
                .filter(w -> w.isPresent())
                .map(w -> w.get())
                .ifPresent(w -> {

                    activityRepository.deleteAll(w.getActivities());

                    Optional.of(w)
                            .map(e -> courseRepository.findCourseByWeeks(e))
                            .filter(e -> e.isPresent())
                            .map(e -> e.get())
                            .map(e -> {
                                e.getWeeks().remove(w);
                                return courseRepository.save(e);
                            })
                            .orElseThrow(() -> new ApiException("Course not  found"));

                    weekRepository.delete(w);
                });
    }


    public boolean isValidWeekTitle(String courseId, String title){
        // Check if the title is blank or null
        if (title == null || title.trim().isEmpty())
            return false;

//        // Check if the title has less than 4 characters
//        if (title.length() < 3)
//            return false;

        // Check if the title consists of repeated spaces
        if (title.matches("^\\s+$"))
            return false;

//        // Check if the title exists on db
//        if (courseRepository.findById(courseId)
//                .get()
//                .getWeeks()
//                .stream()
//                .map(week -> week.getTitle().replaceAll("\\s", ""))
//                .collect(Collectors.toList())
//                .contains(title.replaceAll("\\s", ""))
//        )
////            throw new ApiException("Title not valid");
//            return false;
        // All checks passed, title is valid
        return true;
    }

    public boolean isValidWeekNumber(String courseId, int number){

        if(number <= 0)
            return false;


//        if(courseRepository.findById(courseId)
//                .get()
//                .getWeeks()
//                .stream()
//                .map(week -> week.getNumber())
//                .collect(Collectors.toList())
//                .contains(number)
//        )
////            throw new ApiException("Number not valid");
//            return false;
        return true;

    }
}
