package com.fixcity.fixcity.reportconfirmation.exception;

public final class CannotConfirmOwnReportExceptionReport extends ReportConfirmationException {
    public CannotConfirmOwnReportExceptionReport() {
        super("You cannot confirm your own report.");
    }
}
