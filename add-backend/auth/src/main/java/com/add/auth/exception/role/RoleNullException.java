package com.add.auth.exception.role;

import com.add.auth.exception.config.ApiException;

public class RoleNullException extends ApiException {

  public RoleNullException() {
    super("error Role is null");

  }

}
