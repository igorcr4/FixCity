package com.fixcity.fixcity.report.response;

import java.time.LocalDateTime;

public record ReportResponse(Long id,
                             String imageUrl,
                             String afterImageUrl,
                             String title,
                             String description,
                             String address,
                             Double latitude,
                             Double longitude,
                             String status,
                             String category,
                             LocalDateTime createdAt,
                             LocalDateTime updatedAt,
                             LocalDateTime resolvedAt,
                             Long userId,
                             long confirmationCount,
                             boolean confirmedByCurrentUser,
                             String username) {

    public ReportResponse withConfirmations(long count, boolean confirmed) {
        return new ReportResponse(id, imageUrl, afterImageUrl, title, description,
                address, latitude, longitude, status, category,
                createdAt, updatedAt, resolvedAt, userId, count, confirmed, username);
    }
}
