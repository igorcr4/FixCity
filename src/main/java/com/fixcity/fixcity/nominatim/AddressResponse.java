package com.fixcity.fixcity.nominatim;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AddressResponse(
        @JsonProperty("country_code")
        String countryCode,

        @JsonProperty("ISO3166-2-lvl4")
        String iso3166_2_lvl4,

        String country,
        String state,
        String county,
        String city
) {
}
