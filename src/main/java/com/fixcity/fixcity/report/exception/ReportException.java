package com.fixcity.fixcity.report.exception;

public abstract sealed class ReportException extends RuntimeException
permits UploadImageException, ReportNotFoundException, ModifyReportException{
    public ReportException(String message) {
        super(message);
    }
}
