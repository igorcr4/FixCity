package com.fixcity.fixcity.confirmation.exception;

public final class CannotConfirmOwnReportException extends ConfirmationException {
    public CannotConfirmOwnReportException() {
        super("You cannot confirm your own report.");
    }
}
