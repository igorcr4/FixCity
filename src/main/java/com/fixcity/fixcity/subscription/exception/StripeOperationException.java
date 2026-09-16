package com.fixcity.fixcity.subscription.exception;

public final class StripeOperationException extends SubscriptionException {
    public StripeOperationException(Throwable cause) {
        super("Eroare la comunicarea cu procesatorul de plăți.", cause);
    }
}
