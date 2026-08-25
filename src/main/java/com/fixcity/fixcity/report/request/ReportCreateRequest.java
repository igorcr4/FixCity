package com.fixcity.fixcity.report.request;

import com.fixcity.fixcity.report.Category;


public record ReportCreateRequest(
        String title,
        String description,
        Category category,
        Double latitude,
        Double longitude,
        String address,
        String countryIso2,
        String stateIso2,
        String cityName,
        String stateName,
        String countryName
) {}