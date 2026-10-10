package com.fixcity.fixcity.reportconfirmation.exception;

public final class ReportAlreadyResolvedExceptionReport extends ReportConfirmationException {
    public ReportAlreadyResolvedExceptionReport() {
        super("The report is already resolved and can no longer be confirmed.");
    }
}
