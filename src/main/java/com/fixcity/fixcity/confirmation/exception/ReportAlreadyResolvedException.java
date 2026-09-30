package com.fixcity.fixcity.confirmation.exception;

public final class ReportAlreadyResolvedException extends ConfirmationException {
    public ReportAlreadyResolvedException() {
        super("The report is already resolved and can no longer be confirmed.");
    }
}
