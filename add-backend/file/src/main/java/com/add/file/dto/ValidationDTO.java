package com.add.file.dto;

import javax.validation.constraints.NotBlank;

import com.add.file.constants.ValidationType;
import com.drew.lang.annotations.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValidationDTO {

  @NotNull
  private ValidationType validationType;

  @NotBlank
  @NotNull
  private String comment;

  @NotBlank
  @NotNull
  private String userId;

}
