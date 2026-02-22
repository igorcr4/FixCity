package com.fixcity.fixcity.report.dto;

import java.time.LocalDateTime;

public record ReportResponse(Long id,
                             String imageUrl,
                             String description,
                             Double latitude,
                             Double longitude,
                             String status,
                             LocalDateTime createdAt,
                             LocalDateTime updatedAt,
                             Long userId) {}
