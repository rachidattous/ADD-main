package com.add.file.exception.photo;

public class PhotoNotFoundException extends RuntimeException {

  /**
   * @param message
   */
  public PhotoNotFoundException() {
    super("error photo does not exist");

  }

}
