package com.fixcity.fixcity.geography;

public interface GeographyResolver {
    AdministrativeLocation resolve(double latitude, double longitude);
}