package com.fixcity.fixcity.report.exception;

public final class MunicipalityNotAssignedException extends ReportException {
    public MunicipalityNotAssignedException() {
        super("The user has no associated municipality.");
    }
}
