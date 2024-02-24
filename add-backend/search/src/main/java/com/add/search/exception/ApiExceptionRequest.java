package com.add.search.exception;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.ZonedDateTime;

public class ApiExceptionRequest {

  private final String message;

  @JsonIgnore
  private final Throwable throwable;

  private final HttpStatus httpStatus;

  private final ZonedDateTime timestamp;

  /**
   * @param message
   * @param throwable
   * @param httpStatus
   * @param timestamp
   */
  public ApiExceptionRequest(String message, Throwable throwable, HttpStatus httpStatus, ZonedDateTime timestamp) {
    this.message = message;
    this.throwable = throwable;
    this.httpStatus = httpStatus;
    this.timestamp = timestamp;
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
