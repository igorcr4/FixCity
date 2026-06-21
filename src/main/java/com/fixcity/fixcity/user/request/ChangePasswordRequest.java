package com.fixcity.fixcity.user.request;

public record ChangePasswordRequest(
        String oldPassword,
        String newPassword
) {}
