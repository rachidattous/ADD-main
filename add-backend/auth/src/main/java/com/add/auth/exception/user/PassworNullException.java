package com.add.auth.exception.user;

import com.add.auth.exception.config.ApiException;

public class PassworNullException extends ApiException {

  public PassworNullException() {
    super("null password");

  }

}
