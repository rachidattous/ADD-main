package com.add.auth.constants;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleEnum implements IEnum {

  @JsonProperty("Super Admin")
  SUPERADMIN("Super Admin"),

  @JsonProperty("Admin")
  ADMIN("Admin"),

  @JsonProperty("Validator")
  VALIDATOR("Validator"),

  @JsonProperty("Teacher")
  TEACHER("Teacher"),

  @JsonProperty("Monitor")
  MONITOR("Monitor"),

  @JsonProperty("Student")
  STUDENT("Student");

  private final String value;

  public static RoleEnum getRole(String role) {
    return Arrays.asList(values())
        .stream()
        .filter(r -> r.toString().equals(role))
        .findFirst()
        .orElse(null);
  }

  public static List<String> toString(List<RoleEnum> roles) {
    return roles.stream().filter(Objects::nonNull).map(Object::toString).collect(Collectors.toList());
  }

}
