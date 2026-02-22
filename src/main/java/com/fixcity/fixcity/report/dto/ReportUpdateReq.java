package com.fixcity.fixcity.report.dto;

import com.fixcity.fixcity.report.Status;

import java.time.LocalDateTime;

public record ReportUpdateReq(
        String title,
        String imageUrl,
        String description,
        Status status
) {
}
