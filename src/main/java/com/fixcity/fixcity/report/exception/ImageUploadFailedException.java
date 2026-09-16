package com.fixcity.fixcity.report.exception;

public final class ImageUploadFailedException extends ReportException {
    public ImageUploadFailedException(Throwable cause) {
        super("Imaginea nu a putut fi încărcată din cauza unei erori la serviciul de stocare.", cause);
    }
}
