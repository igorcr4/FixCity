package com.fixcity.fixcity.comment.exception;

public abstract sealed class CommentException extends RuntimeException
permits NotCommentOwnerException, CommentNotFoundException {
    public CommentException(String message) {
        super(message);
    }
}
