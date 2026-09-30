package com.fixcity.fixcity.municipality.exception;

public final class MunicipalityNotFoundException extends MunicipalityException {
    public MunicipalityNotFoundException() {
        super("Municipality not found.");
    }
}
