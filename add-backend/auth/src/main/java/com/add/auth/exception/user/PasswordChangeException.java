package com.add.auth.exception.user;

import com.add.auth.exception.config.ApiException;

public class PasswordChangeException extends ApiException {

  public PasswordChangeException() {
    super("error while changing password");

  }

}
