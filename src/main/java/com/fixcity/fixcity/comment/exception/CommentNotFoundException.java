package com.fixcity.fixcity.comment.exception;

public final class CommentNotFoundException extends CommentException {
    public CommentNotFoundException() {
        super("Comment not found.");
    }
}
