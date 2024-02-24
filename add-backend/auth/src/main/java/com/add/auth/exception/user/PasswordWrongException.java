package com.add.auth.exception.user;

import com.add.auth.exception.config.ApiException;

public class PasswordWrongException extends ApiException {

  public PasswordWrongException() {
    super("wrong password");

  }

}
