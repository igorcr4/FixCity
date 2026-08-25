package com.fixcity.fixcity.nominatim;

import org.springframework.web.client.RestClientException;

public class NominatimClientException extends RuntimeException {
    public NominatimClientException(String message, RestClientException ex) {
        super(message);
    }
}
