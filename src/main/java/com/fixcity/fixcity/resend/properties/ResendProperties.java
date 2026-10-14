package com.fixcity.fixcity.resend.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "resend")
public record ResendProperties(
        String from,
        String baseUrl,
        String apiKey
) {
}
