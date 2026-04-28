package com.fixcity.fixcity.user.admin.response;

import com.fixcity.fixcity.user.role.Role;

public record AdminUserResponse(
        Long id,
        String username,
        String email,
        Role role,
        Long municipalityId,
        String municipalityName
) {
}