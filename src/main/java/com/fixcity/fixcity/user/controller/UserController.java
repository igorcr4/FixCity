package com.fixcity.fixcity.user.controller;

import com.fixcity.fixcity.user.request.ChangePasswordRequest;
import com.fixcity.fixcity.user.request.RegisterUserRequest;
import com.fixcity.fixcity.user.model.UserPrincipal;
import com.fixcity.fixcity.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterUserRequest req) {
        userService.registerUser(req.username(), req.email(), req.password());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/change-password")
    public void changePassword(@AuthenticationPrincipal UserPrincipal principal, @RequestBody ChangePasswordRequest req) {
        userService.changePassword(principal.getUsername(), req.oldPassword(), req.newPassword());
    }
}
