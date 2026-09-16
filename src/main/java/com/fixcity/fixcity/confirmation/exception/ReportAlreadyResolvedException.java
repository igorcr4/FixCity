package com.fixcity.fixcity.confirmation.exception;

public final class ReportAlreadyResolvedException extends ConfirmationException {
    public ReportAlreadyResolvedException() {
        super("Raportul este deja rezolvat și nu mai poate fi confirmat.");
    }
}
