package com.add.communication.exception;

import com.add.communication.exception.config.ApiException;

public class SessionNotExistException extends ApiException {

  public SessionNotExistException(String sessionId) {
    super("Store does not contain SessionId " + sessionId);

  }

}
