package com.add.file.exception.content;

public class ContentNotFoundException extends RuntimeException {

  /**
   * @param message
   */
  public ContentNotFoundException() {
    super("error content does not exist");

  }

}
