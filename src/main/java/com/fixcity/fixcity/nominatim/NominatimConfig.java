package com.fixcity.fixcity.nominatim;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestClient;


@Configuration
@EnableConfigurationProperties(NominatimProperties.class)
public class NominatimConfig {

    @Bean
    public RestClient nominatimRestClient(RestClient.Builder builder, NominatimProperties properties) {
        return builder
                .baseUrl(properties.baseUrl())
                .defaultHeader(HttpHeaders.USER_AGENT, properties.userAgent())
                .build();
    }
}
