package com.fixcity.fixcity.nominatim.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record NominatimResponse(
        @JsonProperty("lat")
        String latitude,

        @JsonProperty("lon")
        String longitude,

        AddressResponse address
) {
}
