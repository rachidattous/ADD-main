package com.add.auth.exception.user;

import com.add.auth.exception.config.ApiException;

public class VerifyEmailException extends ApiException {

  public VerifyEmailException() {
    super("Error while verifying email");

  }

}
