package com.fixcity.fixcity.user.service;

import com.fixcity.fixcity.security.JwtService;
import com.fixcity.fixcity.user.model.User;
import com.fixcity.fixcity.user.exception.IncorrectPasswordException;
import com.fixcity.fixcity.user.model.UserPrincipal;
import com.fixcity.fixcity.user.request.LoginRequest;
import com.fixcity.fixcity.user.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtService jwtService;

    public LoginResponse login(LoginRequest req) {
        User user = userService.findByEmail(req.email());

        if (!passwordEncoder.matches(req.password(), user.getPassword())) {
            throw new IncorrectPasswordException();
        }

        UserPrincipal principal = UserPrincipal.from(user);
        String token = jwtService.generateToken(principal);

        List<String> roles = principal.authorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return new LoginResponse(
                token,
                principal.id(),
                principal.username(),
                principal.email(),
                roles
        );
    }
}
