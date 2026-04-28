package com.fixcity.fixcity.municipality.repository;

import com.fixcity.fixcity.municipality.model.Municipality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MunicipalityRepository extends JpaRepository<Municipality, Long> {

    Optional<Municipality> findByCountryAndStateAndName(String country, String state, String name);
}
