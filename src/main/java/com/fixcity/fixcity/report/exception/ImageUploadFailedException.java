package com.fixcity.fixcity.report.exception;

public final class ImageUploadFailedException extends ReportException {
    public ImageUploadFailedException(Throwable cause) {
        super("The image could not be uploaded due to a storage service error.", cause);
    }
}
