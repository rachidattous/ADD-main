package com.add.auth.exception.auth;

import com.add.auth.exception.config.ApiException;

public class RefreshTokenException extends ApiException {

  public RefreshTokenException() {
    super("error while refreshing token");

  }

}
