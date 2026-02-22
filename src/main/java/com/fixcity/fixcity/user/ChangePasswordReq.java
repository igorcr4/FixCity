package com.fixcity.fixcity.user;

public record ChangePasswordReq(
        String oldPassword,
        String newPassword
) {}
