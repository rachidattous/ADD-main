package com.add.course.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseDTO {

    @NotNull
    @NotBlank
    private String userId;

    @NotNull
    @NotBlank
    private String title;

    @NotNull
    @NotBlank
    private String summary;

//    private String state;
//
//    private String category;
//
//    private String language;


    private MultipartFile image;

}
