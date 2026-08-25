package com.fixcity.fixcity.csc.exception;

public class CscClientException extends RuntimeException {
    public CscClientException(String message) {
        super(message);
    }

    public CscClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
