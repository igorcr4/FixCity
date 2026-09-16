package com.fixcity.fixcity.municipality.exception;

public final class MunicipalityResolutionException extends MunicipalityException {
    public MunicipalityResolutionException(Throwable cause) {
        super("Primăria nu a putut fi rezolvată din cauza unui conflict de concurență.", cause);
    }
}
