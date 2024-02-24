package com.add.file.exception.file;

public class FileDeleteException extends RuntimeException {

  /**
   * @param message
   */
  public FileDeleteException() {
    super("error while deleting a file");

  }

}
