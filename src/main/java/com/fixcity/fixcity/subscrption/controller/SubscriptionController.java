package com.fixcity.fixcity.subscrption.controller;

import com.fixcity.fixcity.municipality.model.Municipality;
import com.fixcity.fixcity.subscrption.enumeration.FeatureType;
import com.fixcity.fixcity.subscrption.request.CheckoutRequest;
import com.fixcity.fixcity.subscrption.response.CheckoutResponse;
import com.fixcity.fixcity.subscrption.response.SubscriptionResponse;
import com.fixcity.fixcity.subscrption.service.FeatureAccessService;
import com.fixcity.fixcity.subscrption.service.SubscriptionService;
import com.fixcity.fixcity.user.model.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Set;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subscriptions")
public class SubscriptionController {
    private final SubscriptionService service;
    private final FeatureAccessService featureAccessService;

    @PostMapping("/checkout")
    public ResponseEntity<CheckoutResponse> createCheckoutSession(@RequestBody CheckoutRequest request) {
        CheckoutResponse checkoutResponse = service.createCheckoutSession(request);
        return ResponseEntity.ok(checkoutResponse);
    }

    @PostMapping("/webhook")
    public ResponseEntity<Void> handleWebhook(@RequestBody byte[] payload,
                                              @RequestHeader("Stripe-Signature") String signature) {
        String convertedPayload = new String(payload, StandardCharsets.UTF_8);
        service.handleWebhook(convertedPayload, signature);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/current")
    public ResponseEntity<SubscriptionResponse> subscriptionResponse(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return service.subscriptionResponse(userPrincipal.municipality().getId())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping("/features")
    public ResponseEntity<Set<FeatureType>> getFeaturesForMunicipality(@AuthenticationPrincipal UserPrincipal user) {
        Long municipalityId = user.municipality().getId();

        Set<FeatureType> features = featureAccessService.getFeaturesForMunicipality(municipalityId);

        return ResponseEntity.ok(features);
    }
}
