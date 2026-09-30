package com.fixcity.fixcity.subscription.exception;

public final class SubscriptionNotFoundException extends SubscriptionException {
    public SubscriptionNotFoundException() {
        super("Subscription not found.");
    }
}
