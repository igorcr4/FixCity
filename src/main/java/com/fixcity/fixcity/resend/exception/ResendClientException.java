package com.fixcity.fixcity.resend.exception;

public final class ResendClientException extends RuntimeException {
    public ResendClientException(Throwable cause) {
        super("Failed to send email via Resend", cause);
    }
}
