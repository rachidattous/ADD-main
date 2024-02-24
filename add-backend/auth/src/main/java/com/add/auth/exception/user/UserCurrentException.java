package com.add.auth.exception.user;

import com.add.auth.exception.config.ApiException;

public class UserCurrentException extends ApiException {

  public UserCurrentException() {
    super("error getting the current user from token");

  }

}
