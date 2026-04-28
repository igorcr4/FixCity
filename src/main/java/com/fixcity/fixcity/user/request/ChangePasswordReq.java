package com.fixcity.fixcity.user.request;

public record ChangePasswordReq(
        String oldPassword,
        String newPassword
) {}
