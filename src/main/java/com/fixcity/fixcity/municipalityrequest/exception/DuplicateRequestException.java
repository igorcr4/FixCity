package com.fixcity.fixcity.municipalityrequest.exception;

public final class DuplicateRequestException extends RequestException {
    public DuplicateRequestException() {
        super("Ai deja o cerere în așteptare.");
    }
}
