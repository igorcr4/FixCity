package com.fixcity.fixcity.comment.exception;

public final class CommentNotFoundException extends CommentException {
    public CommentNotFoundException() {
        super("Comentariul nu a fost găsit.");
    }
}
