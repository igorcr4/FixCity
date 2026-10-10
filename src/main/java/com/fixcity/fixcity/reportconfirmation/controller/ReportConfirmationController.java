package com.fixcity.fixcity.reportconfirmation.controller;

import com.fixcity.fixcity.reportconfirmation.response.ReportConfirmationResponse;
import com.fixcity.fixcity.reportconfirmation.service.ReportConfirmationService;
import com.fixcity.fixcity.user.model.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/confirmations")
public class ReportConfirmationController {
    private final ReportConfirmationService reportConfirmationService;

    @PostMapping("/{reportId}")
    public ResponseEntity<ReportConfirmationResponse> toggle(
            @PathVariable Long reportId,
            @AuthenticationPrincipal UserPrincipal principal) {
        ReportConfirmationResponse response =
                reportConfirmationService.toggleConfirmation(reportId, principal.id());
        return ResponseEntity.ok(response);
    }
}
