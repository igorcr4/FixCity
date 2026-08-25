package com.fixcity.fixcity.csc;

import com.fixcity.fixcity.geography.CityOption;
import com.fixcity.fixcity.geography.CountryOption;
import com.fixcity.fixcity.geography.GeographyCatalog;
import com.fixcity.fixcity.geography.StateOption;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@RequiredArgsConstructor
public class CscGeographyCatalog implements GeographyCatalog {
    private final CountryStateCityClient client;

    @Override
    public List<CountryOption> countryList() {
        return client.getCountries().stream().map(
                country -> new CountryOption(
                        country.iso2(),
                        country.name()
                )
        ).toList();
    }

    @Override
    public List<StateOption> stateList(String countryIso2) {
        return client.getStates(countryIso2).stream().map(
                state -> new StateOption(
                        state.iso2(),
                        state.name()
                )
        ).toList();
    }

    @Override
    public List<CityOption> cityList(String countryIso2, String stateIso2) {
        return client.getCities(countryIso2, stateIso2).stream().map(
                city -> new CityOption(
                        city.name()
                )
        ).toList();
    }
}
