package com.fixcity.fixcity.geography;

import java.util.List;

public interface GeographyCatalog {
    List<CountryOption> countryList();

    List<StateOption> stateList(String countryIso2);

    List<CityOption> cityList(String countryIso2, String stateIso2);
}
