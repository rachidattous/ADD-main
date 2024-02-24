package com.add.auth.dto;

import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

import com.add.auth.constants.Gender;
import com.add.auth.constants.UserStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdate {

  private String firstName;

  private String lastName;

  private String email;

  private List<String> rolesName;

  @JsonFormat(pattern = "yyyy-MM-dd")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate birthDate;

  @JsonFormat(pattern = "yyyy-MM-dd")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate joinedDate;

  private Gender gender;

  private UserStatus userStatus;

  private boolean isEmailVerified;

}
