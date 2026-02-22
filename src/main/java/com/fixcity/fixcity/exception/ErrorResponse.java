package com.fixcity.fixcity.exception;

import java.time.Instant;

public record ErrorResponse(
        String message,
        Instant timestamp,
        String errorCode
) {
}
