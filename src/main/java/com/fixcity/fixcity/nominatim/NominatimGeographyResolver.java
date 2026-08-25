package com.fixcity.fixcity.nominatim;

import com.fixcity.fixcity.geography.AdministrativeLocation;
import com.fixcity.fixcity.geography.GeographyResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NominatimGeographyResolver implements GeographyResolver {
    private final NominatimClient client;

    @Override
    public AdministrativeLocation resolve(double latitude, double longitude) {
        NominatimResponse response = client.reverse(latitude, longitude);
        AddressResponse addressResponse = response.address();

        String countryIso2 = addressResponse.countryCode().toUpperCase();
        String stateIso2 = addressResponse.iso3166_2_lvl4().split("-")[1];
        String country = addressResponse.country();
        String state = firstNonBlank(addressResponse.state(), addressResponse.county());
        String city = addressResponse.city();
        double responseLatitude = Double.parseDouble(response.latitude());
        double responseLongitude = Double.parseDouble(response.longitude());

        return new AdministrativeLocation(
                countryIso2,
                stateIso2,
                country,
                state,
                city,
                responseLatitude,
                responseLongitude
        );
    }

    private String firstNonBlank(String... values) {
        for (String value : values) {
            if(value != null && !value.isBlank()) {
                return value;
            }
        }
        return null;
    }
}
