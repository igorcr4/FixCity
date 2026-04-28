package com.fixcity.fixcity.geography.csc.response;


public record CscCountryResponse(
        Long id,
        String name,
        String iso2,
        String iso3,
        String emoji
) {
}
