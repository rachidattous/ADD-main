package com.add.auth.exception.user;

import com.add.auth.exception.config.ApiException;

public class ForgotPasswordException extends ApiException {

  public ForgotPasswordException() {
    super("error in forgot password");

  }

}
