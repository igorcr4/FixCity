package com.fixcity.fixcity.nominatim;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "nominatim")
public record NominatimProperties(
        String baseUrl,
        String userAgent
) {
}
