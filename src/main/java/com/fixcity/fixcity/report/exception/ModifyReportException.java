package com.fixcity.fixcity.report.exception;

public final class ModifyReportException extends ReportException {
    public ModifyReportException() {
        super("Nu poți modifica un raport străin!");
    }
}
