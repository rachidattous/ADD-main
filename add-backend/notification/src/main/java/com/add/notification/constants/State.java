package com.add.notification.constants;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum State {

    @JsonProperty("Opened")
    OPENED,

    @JsonProperty("Not Opened")
    NOT_OPENED;
}
