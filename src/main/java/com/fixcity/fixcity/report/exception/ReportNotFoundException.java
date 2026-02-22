package com.fixcity.fixcity.report.exception;

public final class ReportNotFoundException extends ReportException {
    public ReportNotFoundException() {
        super("Raportul nu a putut fi găsit!");
    }
}
