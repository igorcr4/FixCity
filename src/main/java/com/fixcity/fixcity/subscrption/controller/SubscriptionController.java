package com.fixcity.fixcity.subscrption.controller;

import com.fixcity.fixcity.subscrption.request.CheckoutRequest;
import com.fixcity.fixcity.subscrption.response.CheckoutResponse;
import com.fixcity.fixcity.subscrption.response.SubscriptionResponse;
import com.fixcity.fixcity.subscrption.service.SubscriptionService;
import com.fixcity.fixcity.user.model.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subscriptions")
public class SubscriptionController {
    private final SubscriptionService service;

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
        SubscriptionResponse response = service.subscriptionResponse(userPrincipal.municipality().getId());
        return ResponseEntity.ok(response);
    }
}
