package com.fixcity.fixcity.resend.request;

public record ResendRequest(
        String from,
        String to,
        String subject,
        String text
) {
}
