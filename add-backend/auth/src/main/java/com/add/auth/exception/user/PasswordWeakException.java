package com.add.auth.exception.user;

import com.add.auth.exception.config.ApiException;

public class PasswordWeakException extends ApiException {

  public PasswordWeakException() {
    super("weak password");

  }

}
