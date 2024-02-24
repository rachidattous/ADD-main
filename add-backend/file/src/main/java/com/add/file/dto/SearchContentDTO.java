package com.add.file.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.add.file.constants.ContentState;
import com.add.file.constants.FileType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchContentDTO {

  private ContentState state;

  private List<String> fileIds;

  private String userId;

  private LocalDate date;

  private LocalTime time;

  private FileType fileType;

  private String ext;

  private String query;

  private List<String> criteria;

}
