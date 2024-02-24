package com.add.course.constants;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
public enum Category{
    @JsonProperty(value = "A")
    A("a"),

    @JsonProperty(value = "AM")
    AM("am"),

    @JsonProperty(value = "B")
    B("b"),

    @JsonProperty(value = "BE")
    BE("be"),

    @JsonProperty(value = "C")
    C("c"),

    @JsonProperty(value = "CE")
    CE("ce"),

    @JsonProperty(value = "D")
    D("d"),

    @JsonProperty(value = "DE")
    DE("de");

    private final String value;

    Category(String value) {
        this.value = value;
    }

    public static Category getByValue(String value) {
        return (value == null) ? null
                : Arrays.asList(values())
                .stream()
                .filter(e -> e.getValue().equals(value))
                .findFirst()
                .orElse(null);
    }
}
