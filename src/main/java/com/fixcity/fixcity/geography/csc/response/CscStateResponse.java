package com.fixcity.fixcity.geography.csc.response;

public record CscStateResponse(
        Long id,
        String name,
        String iso2,
        String type,
        String latitude,
        String longitude
) {
}
