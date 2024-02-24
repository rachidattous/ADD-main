package com.add.file.exception.file;

public class FileDownloadException extends RuntimeException {

  /**
   * @param message
   */
  public FileDownloadException() {
    super("error could not Download File");

  }

}
