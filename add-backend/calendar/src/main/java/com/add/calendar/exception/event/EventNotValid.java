package com.add.calendar.exception.event;

import com.add.calendar.exception.config.ApiException;

public class EventNotValid extends ApiException {

  /**
   * @param message
   */
  public EventNotValid(String message) {
    super(message);

  }

}
