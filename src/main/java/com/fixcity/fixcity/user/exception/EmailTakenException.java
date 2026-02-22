package com.fixcity.fixcity.user.exception;

public final class EmailTakenException extends UserException {
    public EmailTakenException() {
        super("Acest email deja exista!");
    }
}
