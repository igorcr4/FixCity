package com.fixcity.fixcity.geography.csc;

import com.fixcity.fixcity.geography.csc.config.CscProperties;
import com.fixcity.fixcity.geography.csc.response.CscCityResponse;
import com.fixcity.fixcity.geography.csc.response.CscCountryResponse;
import com.fixcity.fixcity.geography.csc.response.CscStateResponse;
import com.fixcity.fixcity.geography.csc.exception.CscClientException;
import com.fixcity.fixcity.geography.csc.exception.CscConfigurationException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CountryStateCityClient {

    private static final ParameterizedTypeReference<List<CscCountryResponse>> COUNTRY_LIST =
            new ParameterizedTypeReference<>() {};
    private static final ParameterizedTypeReference<List<CscStateResponse>> STATE_LIST =
            new ParameterizedTypeReference<>() {};
    private static final ParameterizedTypeReference<List<CscCityResponse>> CITY_LIST =
            new ParameterizedTypeReference<>() {};

    private final RestClient cscRestClient;
    private final CscProperties properties;

    private <T> List<T> getList(String path,
                                ParameterizedTypeReference<List<T>> responseType,
                                String errorMessage,
                                Object... uriVariables) {
        ensureConfigured();
        try {
            List<T> response = cscRestClient.get()
                    .uri(path, uriVariables)
                    .header("X-CSCAPI-KEY", properties.apiKey())
                    .retrieve()
                    .body(responseType);

            return response != null ? response : List.of();
        } catch (RestClientException ex) {
            throw new CscClientException(errorMessage, ex);
        }
    }

    public List<CscCountryResponse> getCountries() {
        return getList("/countries", COUNTRY_LIST, "Nu am putut incarca lista de tari de la CSC.");
    }

    public List<CscStateResponse> getStates(String countryIso2) {
        requirePathValue(countryIso2, "countryIso2");
        return getList(
                "/countries/{countryIso2}/states",
                STATE_LIST,
                "Nu am putut incarca lista de regiuni pentru tara selectata.",
                countryIso2.toUpperCase()
        );
    }

    public List<CscCityResponse> getCities(String countryIso2, String stateIso2) {
        requirePathValue(countryIso2, "countryIso2");
        requirePathValue(stateIso2, "stateIso2");
        return getList(
                "/countries/{countryIso2}/states/{stateIso2}/cities",
                CITY_LIST,
                "Nu am putut incarca lista de orase pentru regiunea selectata.",
                countryIso2.toUpperCase(),
                stateIso2.toUpperCase()
        );
    }

    public List<CscCityResponse> getCitiesByCountry(String countryIso2) {
        requirePathValue(countryIso2, "countryIso2");
        return getList(
                "/countries/{countryIso2}/cities",
                CITY_LIST,
                "Nu am putut incarca lista de orase pentru tara selectata.",
                countryIso2.toUpperCase()
        );
    }

    private void ensureConfigured() {
        if (!StringUtils.hasText(properties.baseUrl())) {
            throw new CscConfigurationException("CSC base URL nu este configurat.");
        }
        if (!StringUtils.hasText(properties.apiKey())) {
            throw new CscConfigurationException("CSC API key nu este configurat.");
        }
    }

    private void requirePathValue(String value, String fieldName) {
        if (!StringUtils.hasText(value)) {
            throw new IllegalArgumentException(fieldName + " este obligatoriu.");
        }
    }
}
