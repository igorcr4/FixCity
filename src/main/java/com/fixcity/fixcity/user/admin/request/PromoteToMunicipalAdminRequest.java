package com.fixcity.fixcity.user.admin.request;

public record PromoteToMunicipalAdminRequest(
        String country,
        String state,
        String city
) {}
