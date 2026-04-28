package com.fixcity.fixcity.user.controller;

import com.fixcity.fixcity.user.request.LoginReq;
import com.fixcity.fixcity.user.response.LoginResponse;
import com.fixcity.fixcity.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginReq req) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(authService.login(req));
    }
}
