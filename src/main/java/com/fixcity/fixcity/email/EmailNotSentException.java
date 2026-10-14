package com.fixcity.fixcity.email;

public class EmailNotSentException extends RuntimeException {
    public EmailNotSentException(Throwable cause) {
        super("Email was not sent", cause);
    }
}
