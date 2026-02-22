package com.fixcity.fixcity.user.exception;

public final class UsernameTakenException extends UserException {
    public UsernameTakenException() {
        super("Acest username deja exista!");
    }
}
