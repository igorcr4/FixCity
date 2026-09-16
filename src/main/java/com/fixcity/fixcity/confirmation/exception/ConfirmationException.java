package com.fixcity.fixcity.confirmation.exception;

public abstract sealed class ConfirmationException extends RuntimeException
permits CannotConfirmOwnReportException, ReportAlreadyResolvedException {
    public ConfirmationException(String message) {
        super(message);
    }
}
