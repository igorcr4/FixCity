package com.fixcity.fixcity.user.exception;

public final class UsernameNotFound extends UserException {
    public UsernameNotFound() {
        super("Utilizatorul nu exista!");
    }
}
