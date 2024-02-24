package com.add.course.services;

import com.add.course.dto.ContentResponseDTO;
import com.add.course.dto.ImageDTO;

import java.util.List;

public interface IRestService {

    List<String> getActiveUserIds();

    ContentResponseDTO saveCourseImage(ImageDTO imageDTO);

    void deleteImage(String imageId);
}