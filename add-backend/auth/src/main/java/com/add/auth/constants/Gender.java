package com.add.auth.constants;

import java.util.Arrays;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Gender implements IEnum {

  @JsonProperty("Male")
  MALE("M"),

  @JsonProperty("Female")
  FEMALE("F"),

  @JsonProperty("NAN")
  NAN("NAN");

  private final String value;

  public static Gender getByValue(String value) {
    return Optional.ofNullable(value)
        .map(val -> Arrays.asList(values())
            .stream()
            .filter(e -> !e.equals(NAN))
            .filter(e -> e.getValue().equals(val))
            .findFirst()
            .orElse(NAN))
        .orElse(NAN);

  }
}
