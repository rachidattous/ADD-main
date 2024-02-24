package com.add.course.constants;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
public enum Language{
    @JsonProperty(value = "French")
    FRENCH("french"),

    @JsonProperty(value = "Arab")
    ARAB("arab"),

    @JsonProperty(value = "English")
    ENGLISH("english"),

    @JsonProperty(value = "Dialect")
    DIALECT("dialect");

    private final String value;

    Language(String value) {
        this.value = value;
    }

    public static Language getByValue(String value) {
        return (value == null) ? null
                : Arrays.asList(values())
                .stream()
                .filter(e -> e.getValue().equals(value))
                .findFirst()
                .orElse(null);
    }
}
