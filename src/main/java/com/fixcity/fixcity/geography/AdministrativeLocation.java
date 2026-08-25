package com.fixcity.fixcity.geography;

public record AdministrativeLocation(
        String countryIso2,
        String stateIso2,
        String country,
        String state,
        String city,
        double latitude,
        double longitude
) {
}
