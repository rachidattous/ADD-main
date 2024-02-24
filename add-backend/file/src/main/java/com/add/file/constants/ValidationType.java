package com.add.file.constants;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ValidationType implements IEnum {

  @JsonProperty(value = "Accepted")
  ACCEPTED("Accepted"),

  @JsonProperty(value = "Refused")
  REFUSED("Refused");

  private final String value;

  public static ValidationType getByValue(String value) {
    return (value == null) ? null
        : Arrays.asList(values())
            .stream()
            .filter(e -> e.getValue().equals(value))
            .findFirst()
            .orElse(null);
  }
}