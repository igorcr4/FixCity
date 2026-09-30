package com.fixcity.fixcity.user.exception;

public abstract sealed class UserException extends RuntimeException
permits UsernameTakenException, EmailTakenException, UsernameNotFoundException, IncorrectPasswordException,
        WeakPasswordException, EmailNotFoundException {
    public UserException(String message) {
        super(message);
    }
}
