package com.add.auth.exception.user;

import com.add.auth.exception.config.ApiException;

public class UserAlreadyExistsException extends ApiException {

  public UserAlreadyExistsException() {
    super("error user already exists");

  }

}
