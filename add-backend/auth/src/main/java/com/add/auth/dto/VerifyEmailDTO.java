package com.add.auth.dto;

import javax.validation.constraints.NotBlank;

import io.smallrye.common.constraint.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerifyEmailDTO {

  @NotBlank
  @NotNull
  private String code;

}
