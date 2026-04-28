package com.fixcity.fixcity.municipality.service;

import com.fixcity.fixcity.municipality.model.Municipality;
import com.fixcity.fixcity.municipality.repository.MunicipalityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MunicipalityService {

    private final MunicipalityRepository municipalityRepository;

    public Municipality findOrCreateMunicipality(String country, String state, String name) {

        return municipalityRepository.findByCountryAndStateAndName(country, state, name)
                .orElseGet(
                        () -> {
                            Municipality municipality = new Municipality();
                            municipality.setCountry(country);
                            municipality.setState(state);
                            municipality.setName(name);

                            municipalityRepository.save(municipality);
                            return municipality;
                        }
                );
    }
}
