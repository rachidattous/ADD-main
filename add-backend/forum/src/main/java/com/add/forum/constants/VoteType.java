package com.add.forum.constants;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum VoteType {

    @JsonProperty("Like")
    LIKE(1l),

    @JsonProperty("Dislike")
    DISLIKE(-1l);

    private final Long direction;

    public static VoteType of(Long direction) {
        return Arrays.stream(values())
                .filter(value -> value.getDirection().equals(direction))
                .findAny()
                .orElse(null);
    }

}
