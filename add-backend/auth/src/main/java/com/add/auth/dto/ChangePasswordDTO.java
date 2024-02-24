package com.add.auth.dto;

import javax.validation.constraints.NotBlank;

import com.add.auth.validation.ChangePasswordValidator;

import io.smallrye.common.constraint.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ChangePasswordValidator
public class ChangePasswordDTO {

  @NotBlank
  @NotNull
  private String oldPassword;

  @NotBlank
  @NotNull
  private String newPassword;

  @NotBlank
  @NotNull
  private String confirmPassword;

  public boolean isValidPassword() {
    return confirmPassword.equals(newPassword);
  }

}
