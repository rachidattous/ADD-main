package com.add.course.exception;

import org.springframework.http.HttpStatus;
import java.time.ZonedDateTime;

public class ApiRequestException {

  private final String message;

  private final Throwable throwable;

  private final HttpStatus httpStatus;

  private final ZonedDateTime timestamp;

  /**
   * @param message
   * @param throwable
   * @param httpStatus
   * @param timestamp
   */
  public ApiRequestException(String message, Throwable throwable, HttpStatus httpStatus,
      ZonedDateTime timestamp) {
    this.message = message;
    this.throwable = throwable;
    this.httpStatus = httpStatus;
    this.timestamp = timestamp;

  }

  /**
   * @param message
   * @param httpStatus
   * @param timestamp
   */
  public ApiRequestException(String message, HttpStatus httpStatus, ZonedDateTime timestamp) {
    this.message = message;
    this.httpStatus = httpStatus;
    this.timestamp = timestamp;
    this.throwable = null;
  }

  /**
   * @return the message
   */
  public String getMessage() {
    return message;
  }

  /**
   * @return the throwable
   */
  public Throwable getThrowable() {
    return throwable;
  }

  /**
   * @return the httpStatus
   */
  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  /**
   * @return the timestamp
   */
  public ZonedDateTime getTimestamp() {
    return timestamp;
  }

}
