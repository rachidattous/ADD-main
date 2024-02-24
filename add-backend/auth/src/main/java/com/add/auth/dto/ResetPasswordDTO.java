package com.add.auth.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordDTO {

  @NotNull
  @NotBlank
  private String password;

  @NotNull
  @NotBlank
  private String confirmPassword;

  public boolean isValid() {
    return password.equals(confirmPassword);
  }

}
