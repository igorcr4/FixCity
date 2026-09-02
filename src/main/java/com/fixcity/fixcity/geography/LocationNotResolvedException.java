package com.fixcity.fixcity.geography;

public class LocationNotResolvedException extends RuntimeException {
    public LocationNotResolvedException(String message) {
        super(message);
    }
}
