package com.fixcity.fixcity.municipalityrequest.controller;
import com.fixcity.fixcity.municipalityrequest.onboarding.RequestSubmission;
import com.fixcity.fixcity.municipalityrequest.response.RequestResponse;
import com.fixcity.fixcity.municipalityrequest.service.RequestService;
import com.fixcity.fixcity.user.model.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
@RequiredArgsConstructor
public class RequestController {
    private final RequestService service;

    @PostMapping("/create")
    public ResponseEntity<RequestResponse> createRequest(@AuthenticationPrincipal UserPrincipal user,
                                                         @RequestBody RequestSubmission submission) {
        RequestResponse request = service.createRequest(user.id(), submission);
        return ResponseEntity.ok(request);
    }

    @PostMapping("/approve/{requestId}")
    public ResponseEntity<Void> approveRequest(@PathVariable Long requestId,
                                               @RequestBody String country, String state, String city) {
        service.approveRequest(requestId, country, state, city);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reject/{requestId}")
    public ResponseEntity<Void> rejectRequest(@PathVariable Long requestId) {
        service.rejectRequest(requestId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/pending")
    public ResponseEntity<List<RequestResponse>> pendingRequests() {
        List<RequestResponse> requests = service.getPendingRequests();
        return ResponseEntity.ok(requests);
    }



}
