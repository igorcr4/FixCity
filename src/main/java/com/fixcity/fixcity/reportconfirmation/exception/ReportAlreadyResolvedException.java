package com.fixcity.fixcity.reportconfirmation.exception;

public final class ReportAlreadyResolvedException extends ReportConfirmationException {
    public ReportAlreadyResolvedException() {
        super("The report is already resolved and can no longer be confirmed.");
    }
}
