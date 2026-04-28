package com.fixcity.fixcity.report.request;

import com.fixcity.fixcity.report.Category;
import com.fixcity.fixcity.report.Status;


public record ReportUpdateRequest(
        String title,
        String description,
        Status status,
        Category category,
        Double latitude,
        Double longitude,
        Boolean removeImage
) {
}
