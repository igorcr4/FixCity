package com.fixcity.fixcity.nominatim;

import com.fasterxml.jackson.annotation.JsonProperty;

public record NominatimResponse(
        @JsonProperty("lat")
        String latitude,

        @JsonProperty("lon")
        String longitude,

        AddressResponse address
) {
}
