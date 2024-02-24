package com.add.file.constants;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
@AllArgsConstructor
public enum ContentState implements IEnum {

    @JsonProperty(value = "Accepted")
    ACCEPTED("Accepted"),

    @JsonProperty(value = "Refused")
    REFUSED("Refused"),

    @JsonProperty(value = "Pending")
    PENDING("Pending");

    private final String value;

    public static ContentState getByValue(String value) {

        return Optional.ofNullable(value)
                .map(val -> Arrays.asList(values())
                        .stream()
                        .filter(e -> e.getValue().equalsIgnoreCase(val))
                        .findFirst()
                        .orElse(PENDING))
                .orElse(PENDING);
    }

    public static ContentState getByValidationType(ValidationType validationType) {
        if (validationType == null) {
            return PENDING;
        } else if (validationType.equals(ValidationType.ACCEPTED)) {
            return ACCEPTED;
        } else if (validationType.equals(ValidationType.REFUSED)) {
            return REFUSED;
        } else {
            return PENDING;
        }

    }
}
