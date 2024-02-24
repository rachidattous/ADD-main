package com.add.communication.exception;

import com.add.communication.exception.config.ApiException;

public class SessionNullException extends ApiException {

  public SessionNullException() {
    super("SessionId or Session object is null");

  }

}
