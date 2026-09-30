package com.fixcity.fixcity.report.exception;

public final class ReportNotFoundException extends ReportException {
    public ReportNotFoundException() {
        super("Report not found.");
    }
}
