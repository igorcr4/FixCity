package com.fixcity.fixcity.user.admin.request;

public record PromoteToMunicipalAdminRequest(
        String countryIso2,
        String stateIso2,
        String name,
        String state,
        String country
) {}
