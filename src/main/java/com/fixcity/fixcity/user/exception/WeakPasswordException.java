package com.fixcity.fixcity.user.exception;

public final class WeakPasswordException extends UserException {
    public WeakPasswordException() {
        super("Parola nu respecta cerintele de complexitate!");
    }
}
