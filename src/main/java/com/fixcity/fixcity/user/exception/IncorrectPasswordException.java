package com.fixcity.fixcity.user.exception;

public final class IncorrectPasswordException extends UserException {
    public IncorrectPasswordException() {
        super("Parola incorecta!");
    }
}
