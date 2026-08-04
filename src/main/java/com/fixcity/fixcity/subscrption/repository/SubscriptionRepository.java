package com.fixcity.fixcity.subscrption.repository;

import com.fixcity.fixcity.municipality.model.Municipality;
import com.fixcity.fixcity.subscrption.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    Optional<Subscription> findByMunicipalityId(Long municipalityId);

    Optional<Subscription> findByStripeSubscriptionId(String stripeSubscriptionId);
}
