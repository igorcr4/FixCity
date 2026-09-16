package com.fixcity.fixcity.municipalityrequest.exception;

public abstract sealed class RequestException extends RuntimeException
permits RequestNotFoundException, RequestNotPendingException, DuplicateRequestException {
    public RequestException(String message) {
        super(message);
    }
}
