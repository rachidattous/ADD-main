package com.add.auth.exception.user;

import com.add.auth.exception.config.ApiException;

public class UserNullException extends ApiException {

  public UserNullException() {
    super("user is null");

  }

}
