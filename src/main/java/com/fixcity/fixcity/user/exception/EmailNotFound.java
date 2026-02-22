package com.fixcity.fixcity.user.exception;

public final class EmailNotFound extends UserException {
    public EmailNotFound() {
        super("Acest email nu exista!");
    }
}
