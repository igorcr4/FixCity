package com.fixcity.fixcity.nominatim.client;

import com.fixcity.fixcity.nominatim.exception.NominatimClientException;
import com.fixcity.fixcity.nominatim.response.NominatimResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Component
@RequiredArgsConstructor
public class NominatimClient {
    private final RestClient nominatimRestClient;

    public NominatimResponse reverse(double latitude, double longitude) {
        try {
            return nominatimRestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/reverse")
                            .queryParam("lat", latitude)
                            .queryParam("lon", longitude)
                            .queryParam("format", "jsonv2")
                            .queryParam("addressdetails", 1)
                            .build())
                    .retrieve()
                    .body(NominatimResponse.class);
        }catch (RestClientException ex){
            throw new NominatimClientException("Reverse geocoding failed", ex);
        }
    }
}
