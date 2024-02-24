package com.add.course.services.impl;

import com.add.course.constants.TypeQuestion;
import com.add.course.dto.*;
import com.add.course.exception.ApiException;
import com.add.course.model.*;
import com.add.course.repository.*;
import com.add.course.services.IActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityService implements IActivityService {

    private final WeekRepository weekRepository;

    private final ActivityRepository activityRepository;

    private final TextRepository textRepository;

    private final ContentRepository contentRepository;

    private final MultimediaRepository multimediaRepository;

    @Override
    public Optional<Week> createTextActivity(String weekId, TextDTO textDTO) {
        return Optional.of(weekId)
                .map(e -> weekRepository.findById(weekId))
                .filter(e -> e.isPresent())
                .map(e -> e.get())
                .map(e -> {

                    if(isValidActivityNumber(weekId, textDTO.getNumber()) && isValidActivityTitle(textDTO.getTitle())) {

                        Activity activity = Text.builder()
                                .number(textDTO.getNumber())
                                .title(textDTO.getTitle())
                                .text(textDTO.getText())
                                .build();

                        activityRepository.save(activity);

                        e.getActivities().add(activity);

                        return Optional.of(weekRepository.save(e));
                    } else
                        throw new ApiException("Title or Number not valid");

                }).orElseThrow(() -> new ApiException("Week with id: "+ weekId +" not  found"));

    }

    @Override
    public Optional<Week> createMultimediaActivity(String weekId, MultimediaDTO multimediaDTO) {
        return Optional.of(weekId)
                .map(e -> weekRepository.findById(weekId))
                .filter(e -> e.isPresent())
                .map(e -> e.get())
                .map(e -> {

                    if(isValidActivityNumber(weekId, multimediaDTO.getNumber()) && isValidActivityTitle(multimediaDTO.getTitle())) {
                        Activity activity = Multimedia.builder()
                                .number(multimediaDTO.getNumber())
                                .title(multimediaDTO.getTitle())
                                .url(multimediaDTO.getUrl())
                                .build();

                        activityRepository.save(activity);

                        e.getActivities().add(activity);

                        return Optional.of(weekRepository.save(e));
                    } else
                        throw new ApiException("Title or Number not valid");

                }).orElseThrow(() -> new ApiException("Week with id: "+ weekId +" not  found"));

    }

    @Override
    public Optional<Week> createContentActivity(String weekId, ContentDTO contentDTO) {
        return Optional.of(weekId)
                .map(e -> weekRepository.findById(weekId))
                .filter(e -> e.isPresent())
                .map(e -> e.get())
                .map(e -> {
                        if (isValidActivityNumber(weekId, contentDTO.getNumber()) && isValidActivityTitle(contentDTO.getTitle())) {
                            Activity activity = Content.builder()
                                    .number(contentDTO.getNumber())
                                    .title(contentDTO.getTitle())
                                    .fileId(contentDTO.getContentId())
                                    .build();

                            activityRepository.save(activity);

                            e.getActivities().add(activity);

                            return Optional.of(weekRepository.save(e));
                        } else
                            throw new ApiException("Title or Number not valid");

                }).orElseThrow(() -> new ApiException("Week with id: "+ weekId +" not  found"));

    }

    @Override
    public List<Activity> getAll(String weekId){
        return Optional.of(weekId)
                .map(e -> weekRepository.findById(weekId))
                .filter(e -> e.isPresent())
                .map(e -> e.get())
                .map(e -> e.getActivities().stream()
                                .sorted(Comparator.comparingInt(Activity::getNumber))
                                .collect(Collectors.toList())
                )
                .orElseThrow(() -> new ApiException("Week with id: "+ weekId +" not  found"));
    }

    @Override
    public Optional<Text> updateTextActivity(String activityId, TextDTO textDTO){
        return Optional.of(activityId)
                .map(e -> textRepository.findById(activityId))
                .filter(e -> e.isPresent())
                .map(e -> e.get())
                .map(e -> {

                    String weekId = weekRepository.findWeekByActivities(e).get().getId();

                    if(isValidActivityTitle(textDTO.getTitle()))
                        e.setTitle(textDTO.getTitle());

                    if(isValidActivityNumber(weekId, textDTO.getNumber()))
                        e.setNumber(textDTO.getNumber());

                    if(isValidText(textDTO.getText()))
                        e.setText(textDTO.getText());

                    return Optional.of(activityRepository.save(e));
                }).orElseThrow( () -> new ApiException("Text Activity with id: "+ activityId +" not  found"));
    }

    @Override
    public Optional<Multimedia> updateMultimediaActivity(String activityId, MultimediaDTO multimediaDTO){
        return Optional.of(activityId)
                .map(e -> multimediaRepository.findById(activityId))
                .filter(e -> e.isPresent())
                .map(e -> e.get())
                .map(e -> {

                    String weekId = weekRepository.findWeekByActivities(e).get().getId();

                    if(isValidActivityTitle(multimediaDTO.getTitle()))
                        e.setTitle(multimediaDTO.getTitle());

                    if(isValidActivityNumber(weekId, multimediaDTO.getNumber()))
                        e.setNumber(multimediaDTO.getNumber());

                    if(isValidText(multimediaDTO.getUrl()))
                        e.setUrl(multimediaDTO.getUrl());

                    return Optional.of(activityRepository.save(e));
                }).orElseThrow( () -> new ApiException("Multimedia Activity with id: "+ activityId +" not  found"));
    }

    @Override
    public Optional<Content> updateContentActivity(String activityId, ContentDTO contentDTO){
        return Optional.of(activityId)
                .map(e -> contentRepository.findById(activityId))
                .filter(e -> e.isPresent())
                .map(e -> e.get())
                .map(e -> {

                    String weekId = weekRepository.findWeekByActivities(e).get().getId();

                    if(isValidActivityTitle(contentDTO.getTitle()))
                        e.setTitle(contentDTO.getTitle());

                    if(isValidActivityNumber(weekId, contentDTO.getNumber()))
                        e.setNumber(contentDTO.getNumber());

                    if(contentDTO.getContentId() != null)
                        e.setFileId(contentDTO.getContentId());

                    return Optional.of(activityRepository.save(e));
                }).orElseThrow( () -> new ApiException("Content Activity with id: "+ activityId +" not  found"));
    }

    @Override
    public void deleteActivity(String activityId){
        Optional.of(activityId)
                .map(a -> activityRepository.findById(a))
                .filter(a -> a.isPresent())
                .map(a -> a.get())
                .ifPresent(a -> {

                    String weekId = weekRepository.findWeekByActivities(a).get().getId();

                    Optional.of(weekId)
                            .map(w -> weekRepository.findById(w))
                            .filter(w -> w.isPresent())
                            .map(w -> w.get())
                            .map(w -> {
                                w.getActivities().remove(a);
                                return weekRepository.save(w);
                            }).orElseThrow(() -> new ApiException("Week not found"));

                    activityRepository.delete(a);
                });
    }

    public boolean isValidActivityTitle(String title){
        // Check if the title is blank or null
        if (title == null || title.trim().isEmpty())
            return false;

        // Check if the title consists of repeated spaces
        if (title.matches("^\\s+$"))
            return false;

        // All checks passed, title is valid
        return true;
    }

    public boolean isValidActivityNumber(String weekId, int number){

        if(number <= 0)
            return false;

        // Check if the number exists on db
        if(weekRepository.findById(weekId)
                .get()
                .getActivities()
                .stream()
                .map(week -> week.getNumber())
                .collect(Collectors.toList())
                .contains(number)
        )
            return false;

        // All checks passed, title is valid
        return true;

    }

    public boolean isValidText(String text){
        // Check if the title is blank or null
        if (text == null || text.trim().isEmpty())
            return false;

        // Check if the title has less than 4 characters
        if (text.length() < 3)
            return false;

        // Check if the title consists of repeated spaces
        if (text.matches("^\\s+$"))
            return false;

        // All checks passed, title is valid
        return true;
    }

}
