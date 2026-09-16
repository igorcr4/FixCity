package com.fixcity.fixcity.subscription.exception;

public abstract sealed class SubscriptionException extends RuntimeException
permits StripeOperationException, SubscriptionNotFoundException, WebhookProcessingException {
    public SubscriptionException(String message) {
        super(message);
    }

    public SubscriptionException(String message, Throwable cause) {
        super(message, cause);
    }
}
