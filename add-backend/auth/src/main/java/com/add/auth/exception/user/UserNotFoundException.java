package com.add.auth.exception.user;

import com.add.auth.exception.config.ApiException;

public class UserNotFoundException extends ApiException {

  public UserNotFoundException() {
    super("user not found");

  }

}
