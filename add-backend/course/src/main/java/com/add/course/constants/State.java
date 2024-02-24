package com.add.course.constants;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum State{
    @JsonProperty(value = "Published")
    PUBLISHED("published"),

    @JsonProperty(value = "Archived")
    ARCHIVED("archived"),

    @JsonProperty(value = "Draft")
    DRAFT("draft");

    private final String value;

    State(String value) {
        this.value = value;
    }

    public static State getByValue(String value) {
        return (value == null) ? null
                : Arrays.asList(values())
                .stream()
                .filter(e -> e.getValue().equals(value))
                .findFirst()
                .orElse(DRAFT);
    }
}
