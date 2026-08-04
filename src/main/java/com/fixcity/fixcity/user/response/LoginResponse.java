package com.fixcity.fixcity.user.response;

import java.util.List;

public record LoginResponse(
        String token,
        Long userId,
        String username,
        String email,
        List<String> roles,
        Long municipalityId
) {
}
