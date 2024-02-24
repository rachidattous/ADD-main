package com.add.course.controllers;

import com.add.course.dto.*;
import com.add.course.model.*;
import com.add.course.services.IActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/course/activity")
public class ActivityController {

    private final IActivityService iActivityService;

    @PostMapping(value = "/text/{weekId}")
    public Optional<Week> createText(@PathVariable String weekId, @RequestBody TextDTO textDTO){
        return iActivityService.createTextActivity(weekId, textDTO);
    }

    @PostMapping(value = "/multimedia/{weekId}")
    public Optional<Week> createMultimedia(@PathVariable String weekId, @RequestBody MultimediaDTO multimediaDTO){
        return iActivityService.createMultimediaActivity(weekId, multimediaDTO);
    }

    @PostMapping(value = "/content/{weekId}")
    public Optional<Week> createContent(@PathVariable String weekId, @RequestBody ContentDTO contentDTO){
        return iActivityService.createContentActivity(weekId, contentDTO);
    }

    @GetMapping(value = "/{weekId}")
    public List<Activity> getAllByWeekId(@PathVariable String weekId){
        return iActivityService.getAll(weekId);
    }

    @PutMapping(value = "/text/{activityId}")
    public Optional<Text> updateText(@PathVariable String activityId, TextDTO textDTO){
        return iActivityService.updateTextActivity(activityId, textDTO);
    }

    @PutMapping(value = "/multimedia/{activityId}")
    public Optional<Multimedia> updateMultimedia(@PathVariable String activityId, MultimediaDTO multimediaDTO){
        return iActivityService.updateMultimediaActivity(activityId, multimediaDTO);
    }

    @PutMapping(value = "/content/{activityId}")
    public Optional<Content> updateContent(@PathVariable String activityId, ContentDTO contentDTO){
        return iActivityService.updateContentActivity(activityId, contentDTO);
    }

    @DeleteMapping(value = "/{activityId}")
    public void deleteActivity(@PathVariable String activityId){
        iActivityService.deleteActivity(activityId);
    }

}
