package com.add.forum.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {

    @NotNull
    @NotBlank
    private String postId;

    @NotNull
    @NotBlank
    private String userId;

    @NotNull
    @NotBlank
    private String text;

}
