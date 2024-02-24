package com.add.search.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class FileContentDTO {

  private String fileId;

  private String title;

  private String content;

  private String description;

  private String ext;

  private String originalFileName;

  private String fileType;

}
