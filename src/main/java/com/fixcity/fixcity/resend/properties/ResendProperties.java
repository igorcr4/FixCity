package com.fixcity.fixcity.resend.properties;


import lombok.NonNull;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "resend")
public record ResendProperties(
        String from,
        String baseUrl,
        String apiKey
) {
    public ResendProperties {
        if(from == null || from.isBlank()) {
            throw new IllegalArgumentException("resend.from must not be blank");
        }

        if(apiKey == null || apiKey.isBlank()) {
            throw new IllegalArgumentException("resend.api-key must not be blank");
        }
    }

    @Override
    @NonNull
    public String toString() {
        return "ResendProperties{" +
                "from='" + from + '\'' +
                ", baseUrl='" + baseUrl + '\'' +
                ", apiKey='" + "****" + '\'' +
                '}';
    }
}
