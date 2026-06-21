package com.fixcity.fixcity.subscrption.request;

import com.fixcity.fixcity.subscrption.enumeration.PlanType;
import lombok.NonNull;

public record CheckoutRequest(
        @NonNull
        Long municipalityId,
        @NonNull
        PlanType plan
) {
}
