package com.fixcity.fixcity.geography.csc.service;

import com.fixcity.fixcity.geography.csc.CountryStateCityClient;
import com.fixcity.fixcity.geography.csc.response.CscCityResponse;
import com.fixcity.fixcity.geography.csc.response.CscCountryResponse;
import com.fixcity.fixcity.geography.csc.response.CscStateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GeographyService {
    private final CountryStateCityClient client;

    public List<CscCountryResponse> getCountries() {
        return client.getCountries();
    }

    public List<CscStateResponse> getStates(String countryIso2) {
        return client.getStates(countryIso2);
    }

    public List<CscCityResponse> getCities(String countryIso2, String stateIso2) {
        return client.getCities(countryIso2, stateIso2);
    }

}
