package com.fixcity.fixcity.subscription.request;

import com.fixcity.fixcity.subscription.enumeration.PlanType;
import lombok.NonNull;

public record CheckoutRequest(
        @NonNull
        Long municipalityId,
        @NonNull
        PlanType plan
) {
}
