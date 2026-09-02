package com.fixcity.fixcity.nominatim;

import com.fixcity.fixcity.geography.AdministrativeLocation;
import com.fixcity.fixcity.geography.GeographyResolver;
import com.fixcity.fixcity.geography.LocationNotResolvedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NominatimGeographyResolver implements GeographyResolver {
    private final NominatimClient client;

    @Override
    public AdministrativeLocation resolve(double latitude, double longitude) {
        NominatimResponse response = client.reverse(latitude, longitude);
        AddressResponse address = response.address();

        if(address == null) {
            throw new LocationNotResolvedException(
                    "Location could not be defined for latitude: " + latitude + " and longitude: " + longitude
            );
        }

        String countryIso2 = extractCountryIso2(latitude, longitude, address);
        String stateIso2 = extractStateIso2(address.iso3166_2_lvl4());
        String country = address.country();
        String state = firstNonBlank(address.state(), address.county());
        String city = address.city();
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

    private static String extractCountryIso2(double latitude, double longitude, AddressResponse addressResponse) {

        String rawCountryCode = addressResponse.countryCode();
        if(rawCountryCode == null || rawCountryCode.isBlank()) {
            throw new LocationNotResolvedException(
                    "Nominatim could not return the country code for latitude: " + latitude + " and longitude: " + longitude
            );
        }
        return rawCountryCode.toUpperCase();
    }

    private static String extractStateIso2(String rawCode) {

        if(rawCode == null || rawCode.isBlank()) {
            return null;
        }
        int dash = rawCode.indexOf('-');

        if(dash < 0) {
            return null;
        }

        return rawCode.substring(dash + 1);
    }

    private static String firstNonBlank(String... values) {
        for (String value : values) {
            if(value != null && !value.isBlank()) {
                return value;
            }
        }
        return null;
    }
}
