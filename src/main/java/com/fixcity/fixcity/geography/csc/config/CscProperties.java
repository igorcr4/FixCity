package com.fixcity.fixcity.geography.csc.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "csc")
public record CscProperties(
        String baseUrl,
        String apiKey
) {
}
