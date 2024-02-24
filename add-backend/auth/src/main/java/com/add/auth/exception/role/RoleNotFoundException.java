package com.add.auth.exception.role;

import com.add.auth.exception.config.ApiException;

public class RoleNotFoundException extends ApiException {

  public RoleNotFoundException() {
    super("error Role not found");

  }

}
