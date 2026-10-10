package com.fixcity.fixcity.reportconfirmation.response;

public record ReportConfirmationResponse(
        boolean confirmed,
        int count
) {
}
