package com.add.auth.dto;

import java.time.LocalDate;

import com.add.auth.constants.Gender;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchUserDTO {

  private String firstName;

  private String lastName;

  private String email;

  private String username;

  private LocalDate birthDate;

  private LocalDate joinedDate;

  private Gender gender;

  private String roleName;

}
