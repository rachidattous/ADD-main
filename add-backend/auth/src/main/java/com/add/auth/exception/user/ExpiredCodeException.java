package com.add.auth.exception.user;

import com.add.auth.exception.config.ApiException;

public class ExpiredCodeException extends ApiException {

  public ExpiredCodeException() {
    super("error expired code");

  }

}
