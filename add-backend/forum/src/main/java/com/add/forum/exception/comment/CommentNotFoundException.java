package com.add.forum.exception.comment;

public class CommentNotFoundException extends RuntimeException {

  public CommentNotFoundException() {
    super("comment not found");

  }

}
