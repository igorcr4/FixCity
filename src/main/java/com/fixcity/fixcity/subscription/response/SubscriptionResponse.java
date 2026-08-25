package com.fixcity.fixcity.subscription.response;

import com.fixcity.fixcity.subscription.enumeration.PlanType;
import com.fixcity.fixcity.subscription.enumeration.SubscriptionStatus;

import java.time.LocalDateTime;

public record SubscriptionResponse(
        PlanType plan,
        SubscriptionStatus status,
        LocalDateTime currentPeriodStart,
        LocalDateTime currentPeriodEnd,
        boolean cancelAtPeriodEnd
) {
}
