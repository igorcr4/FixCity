package com.fixcity.fixcity.municipality.exception;

public abstract sealed class MunicipalityException extends RuntimeException
permits MunicipalityNotFoundException, MunicipalityResolutionException {
    public MunicipalityException(String message) {
        super(message);
    }

    public MunicipalityException(String message, Throwable cause) {
        super(message, cause);
    }
}
