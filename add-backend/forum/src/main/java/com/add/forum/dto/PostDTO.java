package com.add.forum.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {

    private String name;

    private String content;

    private String description;

    private String userId;

    private String subForumId;

}
