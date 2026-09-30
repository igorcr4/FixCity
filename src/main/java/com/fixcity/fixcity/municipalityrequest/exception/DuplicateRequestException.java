package com.fixcity.fixcity.municipalityrequest.exception;

public final class DuplicateRequestException extends RequestException {
    public DuplicateRequestException() {
        super("You already have a pending request.");
    }
}
