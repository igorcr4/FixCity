package com.fixcity.fixcity.confirmation.exception;

public final class CannotConfirmOwnReportException extends ConfirmationException {
    public CannotConfirmOwnReportException() {
        super("Nu îți poți confirma propriul raport.");
    }
}
