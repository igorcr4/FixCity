package com.fixcity.fixcity.user;

import com.fixcity.fixcity.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginReq req) {
        User user = authService.authenticate(req.email(), req.password());

        UserPrincipal principal = UserPrincipal.from(user);

        String token = jwtService.generateToken(principal);

        List<String> role = principal.authorities().stream()
                .map(GrantedAuthority::getAuthority).toList();

        LoginResponse response = new LoginResponse(
                token,
                principal.id(),
                principal.username(),
                principal.email(),
                role
        );

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }
}
