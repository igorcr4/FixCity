package com.fixcity.fixcity.municipalityrequest.exception;

public final class RequestNotFoundException extends RequestException {
    public RequestNotFoundException() {
        super("Cererea nu a fost găsită.");
    }
}
