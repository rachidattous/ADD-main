package com.add.course.constants;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum TypeQuestion{

    // Questions with only one answer
    @JsonProperty(value = "Solus")
    SOLUS("solus"),

    // Questions with multiple answers
    @JsonProperty(value = "Polyresponse")
    POLYRESPONSE("polyresponse");


    private final String value;

    TypeQuestion(String value) {
        this.value = value;
    }


    public static TypeQuestion getByValue(String value) {
        return (value == null) ? null
                : Arrays.asList(values())
                .stream()
                .filter(e -> e.getValue().equals(value))
                .findFirst()
                .orElse(null);
    }
}
