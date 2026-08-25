package com.fixcity.fixcity.subscription.mapper;

import com.fixcity.fixcity.subscription.model.Subscription;
import com.fixcity.fixcity.subscription.response.SubscriptionResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {

    SubscriptionResponse toResponse(Subscription subscription);
}
