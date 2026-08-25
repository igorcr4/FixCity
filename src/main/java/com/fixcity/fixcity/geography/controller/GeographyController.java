package com.fixcity.fixcity.geography.controller;

import com.fixcity.fixcity.geography.AdministrativeLocation;
import com.fixcity.fixcity.geography.GeographyResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/geography")
public class GeographyController {
    private final GeographyResolver geographyResolver;

    @GetMapping("/geocoding")
    public ResponseEntity<AdministrativeLocation> location(@RequestParam("lat") double latitude,
                                                           @RequestParam("lon") double longitude) {
        AdministrativeLocation location = geographyResolver.resolve(latitude, longitude);

        return ResponseEntity.ok(location);
    }
}
