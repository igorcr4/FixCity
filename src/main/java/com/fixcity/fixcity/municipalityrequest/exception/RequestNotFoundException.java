package com.fixcity.fixcity.municipalityrequest.exception;

public final class RequestNotFoundException extends RequestException {
    public RequestNotFoundException() {
        super("Request not found.");
    }
}
