package com.add.auth.exception.auth;

import com.add.auth.exception.config.ApiException;

public class TokenException extends ApiException {

  public TokenException() {
    super("error while getting the token");

  }

}
