package com.add.forum.exception.vote;

public class VoteAlreadySubmittedByUserException extends RuntimeException {

  public VoteAlreadySubmittedByUserException() {
    super("vote already submitted by user");

  }

}
