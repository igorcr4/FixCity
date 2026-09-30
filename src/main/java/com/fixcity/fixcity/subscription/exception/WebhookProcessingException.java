package com.fixcity.fixcity.subscription.exception;

public final class WebhookProcessingException extends SubscriptionException {
    public WebhookProcessingException() {
        super("The Stripe event could not be processed.");
    }
}
