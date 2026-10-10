package com.fixcity.fixcity.reportconfirmation.exception;

public abstract sealed class ReportConfirmationException extends RuntimeException
permits CannotConfirmOwnReportExceptionReport, ReportAlreadyResolvedExceptionReport {
    public ReportConfirmationException(String message) {
        super(message);
    }
}
