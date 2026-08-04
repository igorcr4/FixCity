package com.fixcity.fixcity.confirmation.controller;

import com.fixcity.fixcity.confirmation.response.ConfirmationResponse;
import com.fixcity.fixcity.confirmation.service.ConfirmationService;
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
public class ConfirmationController {
    private final ConfirmationService confirmationService;

    @PostMapping("/{reportId}")
    public ResponseEntity<ConfirmationResponse> toggle(
            @PathVariable Long reportId,
            @AuthenticationPrincipal UserPrincipal principal) {
        ConfirmationResponse response =
                confirmationService.toggleConfirmation(reportId, principal.id());
        return ResponseEntity.ok(response);
    }
}
