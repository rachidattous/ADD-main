package com.add.auth.exception.user;

import com.add.auth.exception.config.ApiException;

public class PasswordConfirmException extends ApiException {

  public PasswordConfirmException() {
    super("error while changing password confirm password not equal the new password");

  }

}
