package com.add.auth.exception.user;

import com.add.auth.exception.config.ApiException;

public class UserUpdateException extends ApiException {

  public UserUpdateException() {
    super("error while updating user");

  }

}
