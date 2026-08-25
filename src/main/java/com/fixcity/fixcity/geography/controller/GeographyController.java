package com.fixcity.fixcity.geography.controller;
import com.fixcity.fixcity.geography.*;
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
    private final GeographyResolver geographyResolver;
    private final GeographyCatalog geographyCatalog;

    @GetMapping("/geocoding")
    public ResponseEntity<AdministrativeLocation> location(@RequestParam("lat") double latitude,
                                                           @RequestParam("lon") double longitude) {
        AdministrativeLocation location = geographyResolver.resolve(latitude, longitude);

        return ResponseEntity.ok(location);
    }

    @GetMapping("/countries")
    public ResponseEntity<List<CountryOption>> getCountries() {
        List<CountryOption> countries = geographyCatalog.countryList();
        return ResponseEntity.ok(countries);
    }

    @GetMapping("/states")
    public ResponseEntity<List<StateOption>> getStates(@RequestParam String countryIso2) {
        List<StateOption> states = geographyCatalog.stateList(countryIso2);
        return ResponseEntity.ok(states);
    }

    @GetMapping("/cities")
    public ResponseEntity<List<CityOption>> getCities(@RequestParam String countryIso2,
                                                      @RequestParam String stateIso2) {
        List<CityOption> cities = geographyCatalog.cityList(countryIso2, stateIso2);
        return ResponseEntity.ok(cities);
    }
}
