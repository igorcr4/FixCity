package com.fixcity.fixcity.report.response;

import java.time.LocalDateTime;

public record ReportResponse(Long id,
                             String imageUrl,
                             String title,
                             String description,
                             String address,
                             Double latitude,
                             Double longitude,
                             String status,
                             String category,
                             LocalDateTime createdAt,
                             LocalDateTime updatedAt,
                             Long userId,
                             String username) {}
