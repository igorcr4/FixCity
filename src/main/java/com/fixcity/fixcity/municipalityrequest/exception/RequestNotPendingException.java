package com.fixcity.fixcity.municipalityrequest.exception;

public final class RequestNotPendingException extends RequestException {
    public RequestNotPendingException() {
        super("The request is not pending and cannot be processed.");
    }
}
