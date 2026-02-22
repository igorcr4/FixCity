package com.fixcity.fixcity.user.exception;

public abstract sealed class UserException extends RuntimeException
permits UsernameTakenException, EmailTakenException, UsernameNotFound, IncorrectPasswordException,
        WeakPasswordException, EmailNotFound {
    public UserException(String message) {
        super(message);
    }
}
