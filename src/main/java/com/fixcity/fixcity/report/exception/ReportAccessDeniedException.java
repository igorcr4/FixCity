package com.fixcity.fixcity.report.exception;

public final class ReportAccessDeniedException extends ReportException {
    public ReportAccessDeniedException() {
        super("You do not have permission to access this report.");
    }
}
