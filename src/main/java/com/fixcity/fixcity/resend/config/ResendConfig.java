package com.fixcity.fixcity.resend.config;

import com.fixcity.fixcity.resend.properties.ResendProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestClient;

@Configuration
@EnableConfigurationProperties(ResendProperties.class)
public class ResendConfig {

    @Bean
    public RestClient resendRestClient(RestClient.Builder builder, ResendProperties properties) {
        return builder
                .baseUrl(properties.baseUrl())
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + properties.apiKey())
                .build();
    }
}
