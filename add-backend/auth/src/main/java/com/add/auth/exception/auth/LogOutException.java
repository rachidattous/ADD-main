package com.add.auth.exception.auth;

import com.add.auth.exception.config.ApiException;

public class LogOutException extends ApiException {

  public LogOutException() {
    super("error while logging out");

  }

}
