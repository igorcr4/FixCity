package com.fixcity.fixcity.comment.exception;

public final class NotCommentOwnerException extends CommentException {
    public NotCommentOwnerException() {
        super("You can only edit your own comments.");
    }
}
