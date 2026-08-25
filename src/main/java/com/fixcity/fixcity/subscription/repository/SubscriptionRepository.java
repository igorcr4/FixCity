package com.fixcity.fixcity.subscription.repository;

import com.fixcity.fixcity.subscription.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    Optional<Subscription> findByMunicipalityId(Long municipalityId);

    Optional<Subscription> findByStripeSubscriptionId(String stripeSubscriptionId);
}
