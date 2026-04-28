package com.fixcity.fixcity.user.admin.controller;

import com.fixcity.fixcity.user.admin.service.AdminService;
import com.fixcity.fixcity.user.admin.response.AdminUserResponse;
import com.fixcity.fixcity.user.admin.request.PromoteToMunicipalAdminRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PatchMapping("/user/{userId}/promote-municipal-admin")
    public void promoteToMunicipalAdmin(@PathVariable Long userId,
                                        @RequestBody PromoteToMunicipalAdminRequest request) {
        adminService.promoteToMunicipalAdmin(userId, request.country(), request.state(), request.city());
    }

    @PatchMapping("/user/{userId}/demote-to-user")
    public void demoteToRegularUser(@PathVariable Long userId) {

        adminService.demoteMunicipalAdminToUser(userId);
    }

    @GetMapping("/users/username")
    public ResponseEntity<AdminUserResponse> findByUsername(@RequestParam String username) {
        return ResponseEntity.ok(adminService.findByUsername(username));
    }
}
