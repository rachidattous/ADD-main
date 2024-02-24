package com.add.auth.exception.user;

import com.add.auth.exception.config.ApiException;

public class UserCreationException extends ApiException {

  public UserCreationException() {
    super("user not created");

  }

}
