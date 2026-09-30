package com.fixcity.fixcity.subscription.exception;

public final class StripeOperationException extends SubscriptionException {
    public StripeOperationException(Throwable cause) {
        super("Error while communicating with the payment processor.", cause);
    }
}
