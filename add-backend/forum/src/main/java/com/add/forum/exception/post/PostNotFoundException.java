package com.add.forum.exception.post;

public class PostNotFoundException extends RuntimeException {

  public PostNotFoundException() {
    super("post not found");

  }

}
