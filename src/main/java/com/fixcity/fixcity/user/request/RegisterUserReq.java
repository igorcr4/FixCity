package com.fixcity.fixcity.user.request;

public record RegisterUserReq(
        String username,
        String email,
        String password
) {
}
