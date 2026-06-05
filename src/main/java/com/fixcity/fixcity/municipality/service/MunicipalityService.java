package com.fixcity.fixcity.municipality.service;

import com.fixcity.fixcity.municipality.model.Municipality;
import com.fixcity.fixcity.municipality.repository.MunicipalityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MunicipalityService {

    private final MunicipalityRepository municipalityRepository;

    public Municipality findOrCreateMunicipality(String country, String state, String name) {
        normalizeText(country);
        normalizeText(state);
        normalizeText(name);

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

    @Transactional(readOnly = true)
    public Municipality findById(Long id) {
        return municipalityRepository.findById(id).orElseThrow();
    }

    public String normalizeText(String value) {
        if(value == null) {
            return "";
        }
        return value.toLowerCase().trim();
    }
}
