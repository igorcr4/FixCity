package com.fixcity.fixcity.subscrption.service;

import com.fixcity.fixcity.subscrption.enumeration.FeatureType;
import com.fixcity.fixcity.subscrption.enumeration.PlanType;
import com.fixcity.fixcity.subscrption.model.Subscription;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class FeatureAccessService {
    private Map<PlanType, Set<FeatureType>> planFeaturesMap;

    @PostConstruct
    private void initPlanFeaturesMap() {
        Set<FeatureType> urbanFeatures = new HashSet<>();
        urbanFeatures.add(FeatureType.PERIOD_FILTER);
        urbanFeatures.add(FeatureType.STATUS_HISTORY);
        urbanFeatures.add(FeatureType.BEFORE_AFTER_RESOLUTION);
        urbanFeatures.add(FeatureType.PERIOD_STATISTICS);
        urbanFeatures.add(FeatureType.TOP_PROBLEM_CATEGORIES);
        urbanFeatures.add(FeatureType.TOP_CRITICAL_ZONES);
        urbanFeatures.add(FeatureType.CSV_EXPORT);
        urbanFeatures.add(FeatureType.EXTENDED_ADMIN_ACCOUNTS);

        Set<FeatureType> cityProFeatures = new HashSet<>(urbanFeatures);
        cityProFeatures.add(FeatureType.CITIZEN_CONFIRMATION_STATISTICS);
        cityProFeatures.add(FeatureType.AUTOMATIC_PRIORITY);
        cityProFeatures.add(FeatureType.PRIORITY_SCORE);
        cityProFeatures.add(FeatureType.AVERAGE_RESOLUTION_TIME);
        cityProFeatures.add(FeatureType.ADVANCED_STATISTICS);

        Map<PlanType, Set<FeatureType>> featuresByPlan = new HashMap<>();
        featuresByPlan.put(PlanType.URBAN, urbanFeatures);
        featuresByPlan.put(PlanType.CITY_PRO, cityProFeatures);

        planFeaturesMap = featuresByPlan;
    }

    private boolean hasFeature(Subscription subscription, FeatureType feature) {
        if (subscription == null || !subscription.isActive()) {
            return false;
        }

        PlanType plan = subscription.getPlan();
        Set<FeatureType> features = planFeaturesMap.get(plan);

        return features.contains(feature);

    }

    public int getMaxAdminAccounts(PlanType plan) {

        if(plan == PlanType.URBAN) {
            return 2;
        } else {
            return 7;
        }
    }

    public Set<FeatureType> getFeaturesForPlan(PlanType plan) {
        Map<PlanType, Set<FeatureType>> planFeaturesMap = buildPlanFeaturesMap();

        if (plan == null) {
            throw new IllegalArgumentException("Planul nu poate fi null.");
        }

        if (!planFeaturesMap.containsKey(plan)) {
            throw new IllegalArgumentException("Nu exista functionalitati definite pentru planul: " + plan);
        }

        return planFeaturesMap.get(plan);
    }
}
