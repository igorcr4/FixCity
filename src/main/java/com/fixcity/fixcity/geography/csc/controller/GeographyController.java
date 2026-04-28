package com.fixcity.fixcity.geography.csc.controller;

import com.fixcity.fixcity.geography.csc.response.CscCityResponse;
import com.fixcity.fixcity.geography.csc.response.CscCountryResponse;
import com.fixcity.fixcity.geography.csc.response.CscStateResponse;
import com.fixcity.fixcity.geography.csc.service.GeographyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/geography")
public class GeographyController {
    private final GeographyService geographyService;

    @GetMapping("/countries")
    public ResponseEntity<List<CscCountryResponse>> getCountries() {
        List<CscCountryResponse> countries = geographyService.getCountries();
        return ResponseEntity.ok(countries);
    }

    @GetMapping("/states")
    public ResponseEntity<List<CscStateResponse>> getStates(@RequestParam String countryIso2) {
        List<CscStateResponse> states = geographyService.getStates(countryIso2);
        return ResponseEntity.ok(states);
    }

    @GetMapping("/cities")
    public ResponseEntity<List<CscCityResponse>> getCities(@RequestParam String countryIso2,
                                                           @RequestParam String stateIso2) {
        List<CscCityResponse> cities = geographyService.getCities(countryIso2, stateIso2);
        return ResponseEntity.ok(cities);
    }
}
