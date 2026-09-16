package com.fixcity.fixcity.subscription.exception;

public final class WebhookProcessingException extends SubscriptionException {
    public WebhookProcessingException() {
        super("Event-ul Stripe nu a putut fi procesat.");
    }
}
