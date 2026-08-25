package com.fixcity.fixcity.municipality.repository;

import com.fixcity.fixcity.municipality.model.Municipality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MunicipalityRepository extends JpaRepository<Municipality, Long> {

    Optional<Municipality> findByCountryAndStateAndName(String country, String state, String name);

    Optional<Municipality> findByStripeCustomerId(String customerId);

    Optional<Municipality> findByCountryIso2AndStateIso2AndNameKey(
            String countryIso2, String stateIso2, String name);
}
