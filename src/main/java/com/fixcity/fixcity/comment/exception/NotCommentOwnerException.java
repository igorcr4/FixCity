package com.fixcity.fixcity.comment.exception;

public final class NotCommentOwnerException extends CommentException {
    public NotCommentOwnerException() {
        super("Poți edita doar propriile comentarii.");
    }
}
