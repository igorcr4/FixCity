package com.fixcity.fixcity.email;

public record EmailMessage(
        String to,
        String subject,
        String text
) {
}
