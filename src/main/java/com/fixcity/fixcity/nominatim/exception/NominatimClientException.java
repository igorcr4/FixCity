package com.fixcity.fixcity.nominatim.exception;

import org.springframework.web.client.RestClientException;

public class NominatimClientException extends RuntimeException {
    public NominatimClientException(Throwable cause) {
        super("Reverse geocoding failed", cause);
    }
}