package com.fixcity.fixcity.subscrption.mapper;

import com.fixcity.fixcity.subscrption.model.Subscription;
import com.fixcity.fixcity.subscrption.response.SubscriptionResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {

    SubscriptionResponse toResponse(Subscription subscription);
}
