package com.add.forum.exception.vote;

public class VoteNotFoundException extends RuntimeException {

  public VoteNotFoundException() {
    super("vote not found");

  }

}
