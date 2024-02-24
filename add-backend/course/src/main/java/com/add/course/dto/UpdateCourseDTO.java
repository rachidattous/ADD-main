package com.add.course.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCourseDTO {

    private String userId;

    private String title;

    private String summary;

    private String state;

    private String category;

    private String language;

    private MultipartFile image;
}
