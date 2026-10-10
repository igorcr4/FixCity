package com.fixcity.fixcity.reportconfirmation.exception;

public final class CannotConfirmOwnReportException extends ReportConfirmationException {
    public CannotConfirmOwnReportException() {
        super("You cannot confirm your own report.");
    }
}
