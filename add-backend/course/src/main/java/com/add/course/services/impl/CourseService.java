package com.add.course.services.impl;

import com.add.course.constants.Category;
import com.add.course.constants.Language;
import com.add.course.constants.State;
import com.add.course.dto.*;
import com.add.course.exception.ApiException;
import com.add.course.model.Course;
import com.add.course.repository.ActivityRepository;
import com.add.course.repository.CourseRepository;
import com.add.course.repository.WeekRepository;
import com.add.course.services.ICourseService;
import com.add.course.services.IRestService;
import com.add.course.specification.CourseSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CourseService implements ICourseService {

    private final CourseRepository courseRepository;

    private final IRestService iRestService;

    private final WeekRepository weekRepository;

    private final ActivityRepository activityRepository;


    @Override
    public Optional<Course> createCourse(CourseDTO courseDTO) {

        return Optional.of(courseDTO.getUserId())
                .map(e -> {
                    if(iRestService.getActiveUserIds().contains(e)){
                        return Optional.of(courseDTO)
                                .map(c -> courseRepository.save(courseDtoToCourse(courseDTO)));
                    } else
                        throw new ApiException("User not found");
                })
                .orElse(Optional.empty());
    }

    @Override
    public Optional<Course> updateCourse(String courseId, UpdateCourseDTO updateCourseDTO){
        return Optional.of(courseId)
                .map(e -> courseRepository.findById(e))
                .filter(e -> e.isPresent())
                .map(e -> e.get())
                .map(e -> {
                    return updateFields(e, updateCourseDTO);
                })
                .orElseThrow(() -> new ApiException("Course not found"));
    }

    @Override
    public Page<Course> getCoursesByUserId(String userId, Pageable pageable){
        if(iRestService.getActiveUserIds().contains(userId))
            return courseRepository.getAllByUserId(userId, pageable);
        else
            throw new ApiException("User not found");
    }

    @Override
    public Page<Course> searchCourses(SearchCourseDTO searchCourseDTO, Pageable pageable) {
        return courseRepository.findAll(CourseSpecification.searchRequest(searchCourseDTO), pageable);
//        return courseRepository.findAll(CourseSpecification.searchRequest(searchCourseDTO), pageable)
//                .map(e -> {
//
//                    Course course = courseRepository.findById(e.getId()).orElseThrow(() -> new ApiException("user not found"));
//
//                    return CourseResponseDTO.builder()
//                            .id(course.getId())
//                            .title(course.getTitle())
//                            .summary(course.getSummary())
//                            .createdDate(course.getCreatedDate())
//                            .createdTime(course.getCreatedTime())
//                            .state(course.getState())
//                            .language(course.getLanguage())
//                            .category(course.getCategory())
//                            .imageId(course.getImageId())
//                            .weeks(courseRepository.findById(e.getId()).get().getWeeks())
//                            .build();
//
//                });
    }

    @Override
    public void deleteCourse(String userId, String courseId){
        Optional.of(courseId)
                .map(e -> courseRepository.findById(e))
                .filter(e -> e.isPresent())
                .map(e -> e.get())
                .ifPresent(e -> {
                    if(e.getUserId().equals(userId)) {
                        e.getWeeks().stream().forEach(week -> {
                            week.getActivities().stream().forEach(activity -> {
                                activityRepository.delete(activity);
                            });
                            weekRepository.delete(week);
                        });
                        iRestService.deleteImage(e.getImageId());
                        courseRepository.delete(e);
                    }
                    else
                        throw new ApiException("Access to this course is denied");
                });
    }

    public boolean isValidCourseText(String text) {
        // Check if the title is blank or null
        if (text == null || text.trim().isEmpty())
            return false;

        // Check if the title has less than 4 characters
//        if (title.length() < 4)
//            return false;

        // Check if the title consists of repeated spaces
        if (text.matches("^\\s+$"))
            return false;

        // Check if the title exists on db
//        if (courseRepository.findAll()
//                .stream()
//                .map(course -> course.getTitle().replaceAll("\\s", ""))
//                .collect(Collectors.toList())
//                .contains(title.replaceAll("\\s", "")))
//            return false;

        // All checks passed, title is valid
        return true;
    }

    public boolean isValidState(String value){

        if(Arrays.asList("draft", "published", "archived").contains(value))
            return true;

        return false;

    }

    public boolean isValidCategory(String value){

        if(Arrays.asList("a", "am", "b","be", "c", "ce", "d", "de").contains(value))
            return true;

        return false;

    }

    public boolean isValidLanguage(String value){

        if(Arrays.asList("french", "english", "dialect", "arab").contains(value))
            return true;

        return false;

    }

    public Course courseDtoToCourse(CourseDTO courseDTO){
        if(courseDTO == null)
            return null;

        Course course = Course.builder()
                .title(courseDTO.getTitle())
                .summary(courseDTO.getSummary())
//                .language(Language.getByValue(courseDTO.getLanguage()))
//                .category(Category.getByValue(courseDTO.getCategory()))
//                .state((courseDTO.getState().equals("published") ||  courseDTO.getState().equals("archived")) ? State.getByValue(courseDTO.getState()) : State.DRAFT)
                .userId(courseDTO.getUserId())
                .build();

        Optional.of(courseDTO.getImage())
                .ifPresent(e -> {
                    ImageDTO imageDTO = ImageDTO.builder()
                            .userId(courseDTO.getUserId())
                            .title(courseDTO.getTitle())
                            .description(courseDTO.getSummary())
                            .file(e)
                            .build();

                    course.setImageId(iRestService.saveCourseImage(imageDTO).getId());
                });

        return course;
    }

    public ImageDTO updateCourseDTOToImageDTO(UpdateCourseDTO updateCourseDTO) {
        return ImageDTO.builder()
                .userId(updateCourseDTO.getUserId())
                .title(updateCourseDTO.getTitle())
                .description(updateCourseDTO.getSummary())
                .file(updateCourseDTO.getImage())
                .build();
    }

    public Optional<Course> updateFields(Course e, UpdateCourseDTO updateCourseDTO){
        if(e.getUserId().equals(updateCourseDTO.getUserId())) {

            if(isValidCourseText(updateCourseDTO.getTitle()))
                e.setTitle(updateCourseDTO.getTitle());

            if(isValidCourseText(updateCourseDTO.getSummary()))
                e.setSummary(updateCourseDTO.getSummary());

            if(isValidState(updateCourseDTO.getState()))
                e.setState(State.getByValue(updateCourseDTO.getState()));

            if(isValidCategory(updateCourseDTO.getCategory()))
                e.setCategory(Category.getByValue(updateCourseDTO.getCategory()));

            if(isValidLanguage(updateCourseDTO.getLanguage()))
                e.setLanguage(Language.getByValue(updateCourseDTO.getLanguage()));

            if(updateCourseDTO.getImage() != null){
                if(e.getImageId() != null)
                    iRestService.deleteImage(e.getImageId());

                e.setImageId(iRestService.saveCourseImage(
                        updateCourseDTOToImageDTO(updateCourseDTO)).getId()
                );

            }

            return Optional.of(courseRepository.save(e));
        } else
            throw new ApiException("Access to this course is denied");
    }


}
