package com.fixcity.fixcity.user.request;

public record LoginRequest(
        String email,
        String password
) {}
