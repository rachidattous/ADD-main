package com.add.communication.exception;

import com.add.communication.exception.config.ApiException;

public class StoreNullException extends ApiException {

  public StoreNullException() {
    super("Store object is null");

  }

}
