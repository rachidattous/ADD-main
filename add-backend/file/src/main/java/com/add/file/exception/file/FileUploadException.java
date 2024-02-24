package com.add.file.exception.file;

public class FileUploadException extends RuntimeException {

  /**
   * @param message
   */
  public FileUploadException() {
    super("error could not upload File");

  }

}
