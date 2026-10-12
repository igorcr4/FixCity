package com.fixcity.fixcity.nominatim.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "nominatim")
public record NominatimProperties(
        String baseUrl,
        String userAgent
) {
}
