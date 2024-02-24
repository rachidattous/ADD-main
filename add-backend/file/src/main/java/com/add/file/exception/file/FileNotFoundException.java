package com.add.file.exception.file;

public class FileNotFoundException extends RuntimeException {

  /**
   * @param message
   */
  public FileNotFoundException() {
    super("error File does not exist");

  }

}
