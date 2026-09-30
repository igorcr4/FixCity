package com.fixcity.fixcity.municipality.exception;

public final class MunicipalityResolutionException extends MunicipalityException {
    public MunicipalityResolutionException(Throwable cause) {
        super("The municipality could not be resolved due to a concurrency conflict.", cause);
    }
}
