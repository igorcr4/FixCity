package com.fixcity.fixcity.confirmation.response;

public record ConfirmationResponse(
        boolean confirmed,
        int count
) {
}
