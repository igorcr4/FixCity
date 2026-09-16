package com.fixcity.fixcity.municipalityrequest.exception;

public final class RequestNotPendingException extends RequestException {
    public RequestNotPendingException() {
        super("Cererea nu este în așteptare și nu poate fi procesată.");
    }
}
