package com.add.file.exception.content;

public class ContentUploadException extends RuntimeException {

  /**
   * @param message
   */
  public ContentUploadException() {
    super("error could not upload content");

  }

}
