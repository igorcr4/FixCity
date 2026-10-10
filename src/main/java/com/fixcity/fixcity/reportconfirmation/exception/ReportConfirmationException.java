package com.fixcity.fixcity.reportconfirmation.exception;

public abstract sealed class ReportConfirmationException extends RuntimeException
permits CannotConfirmOwnReportException, ReportAlreadyResolvedException {
    public ReportConfirmationException(String message) {
        super(message);
    }
}
