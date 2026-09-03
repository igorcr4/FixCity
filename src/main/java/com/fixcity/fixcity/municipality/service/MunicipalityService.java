package com.fixcity.fixcity.municipality.service;
import com.fixcity.fixcity.municipality.model.Municipality;
import com.fixcity.fixcity.municipality.repository.MunicipalityRepository;
import com.fixcity.fixcity.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;
import java.util.List;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class MunicipalityService {

    private final MunicipalityRepository municipalityRepository;

    private static final Pattern DIACRITICS = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");


    public Municipality findOrCreateMunicipality(String countryIso2, String stateIso2, String name,
                                                 String state, String country) {

        String normalizedName = normalizeCityName(name);

        try {
            return municipalityRepository.findByCountryIso2AndStateIso2AndNameKey(countryIso2, stateIso2, normalizedName)
                    .orElseGet(
                            () -> {
                                Municipality municipality = new Municipality();

                                municipality.setCountry(country);
                                municipality.setState(state);
                                municipality.setName(name);

                                municipality.setCountryIso2(countryIso2);
                                municipality.setStateIso2(stateIso2);
                                municipality.setNameKey(normalizedName);

                                municipalityRepository.saveAndFlush(municipality);
                                return municipality;
                            }
                    );
        }catch (DataIntegrityViolationException ex) {
            return municipalityRepository.findByCountryIso2AndStateIso2AndNameKey(countryIso2, stateIso2, normalizedName).orElseThrow();
        }

    }

    public int getMunicipalityAdmins(Municipality municipality) {
        List<User> users = municipality.getUsers();
        return users.size();
    }

    public Municipality findByStripeCustomerId(String customerId) {
        return municipalityRepository.findByStripeCustomerId(customerId).orElseThrow();//exceptie personalizata
    }


    @Transactional(readOnly = true)
    public Municipality findById(Long id) {
        return municipalityRepository.findById(id).orElseThrow();
    }

    private static String normalizeCityName(String cityName) {
        String name = cityName.toLowerCase().trim();

        name = Normalizer.normalize(name, Normalizer.Form.NFD);

        return DIACRITICS.matcher(name).replaceAll("");
    }

}
