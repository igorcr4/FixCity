package com.fixcity.fixcity.municipalityrequest.response;

import com.fixcity.fixcity.municipalityrequest.status.RequestStatus;

import java.time.LocalDateTime;

public record RequestResponse(
        Long id,
        RequestStatus status,
        LocalDateTime createdAt,
        String institutionName,
        String employeePosition,
        String justification,
        String username
) {
}
