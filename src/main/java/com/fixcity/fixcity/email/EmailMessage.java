package com.fixcity.fixcity.email;

public record EmailMessage(
        String to,
        String subject,
        String text
) {
    public EmailMessage {
        if(to == null || to.isBlank()) {
            throw new IllegalArgumentException("to must be non blank");
        }
    }
}
