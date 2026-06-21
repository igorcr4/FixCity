package com.fixcity.fixcity.user.request;

public record RegisterUserRequest(
        String username,
        String email,
        String password
) {
}
