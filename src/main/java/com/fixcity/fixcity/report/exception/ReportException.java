package com.fixcity.fixcity.report.exception;

public abstract sealed class ReportException extends RuntimeException
permits UploadImageException, ReportNotFoundException, ModifyReportException,
        ImageUploadFailedException, MunicipalityNotAssignedException {
    public ReportException(String message) {
        super(message);
    }

    public ReportException(String message, Throwable cause) {
        super(message, cause);
    }
}
