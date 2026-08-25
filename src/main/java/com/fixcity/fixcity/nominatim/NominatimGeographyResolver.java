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

        String countryIso2 = response.address().countryCode().toUpperCase();
        String stateIso2 = response.address().iso3166_2_lvl4().split("-")[1];
        String country = response.address().country();
        String state = response.address().state();
        String city = response.address().city();
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
}
