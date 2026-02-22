package com.fixcity.fixcity.user;

public record RegisterUserReq(
        String username,
        String email,
        String password
) {
}
