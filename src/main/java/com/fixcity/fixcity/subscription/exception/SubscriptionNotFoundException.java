package com.fixcity.fixcity.subscription.exception;

public final class SubscriptionNotFoundException extends SubscriptionException {
    public SubscriptionNotFoundException() {
        super("Abonamentul nu a fost găsit.");
    }
}
