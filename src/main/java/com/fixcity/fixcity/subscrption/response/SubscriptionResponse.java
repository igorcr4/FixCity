package com.fixcity.fixcity.subscrption.response;

import com.fixcity.fixcity.subscrption.enumeration.PlanType;
import com.fixcity.fixcity.subscrption.enumeration.SubscriptionStatus;

import java.time.LocalDateTime;

public record SubscriptionResponse(
        PlanType plan,
        SubscriptionStatus status,
        LocalDateTime currentPeriodStart,
        LocalDateTime currentPeriodEnd,
        boolean cancelAtPeriodEnd
) {
}
