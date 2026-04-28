package com.fixcity.fixcity.user.admin.service;

import com.fixcity.fixcity.municipality.service.MunicipalityService;
import com.fixcity.fixcity.municipality.model.Municipality;
import com.fixcity.fixcity.user.admin.response.AdminUserResponse;
import com.fixcity.fixcity.user.role.Role;
import com.fixcity.fixcity.user.model.User;
import com.fixcity.fixcity.user.repository.UserRepository;
import com.fixcity.fixcity.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserService userService;
    private final UserRepository userRepository;
    private final MunicipalityService municipalityService;

    public void promoteToMunicipalAdmin(Long userId, String country, String state, String city) {

        User user = userService.findById(userId);

        Municipality municipality = municipalityService.findOrCreateMunicipality(country, state, city);

        if(!user.getRole().equals(Role.ROLE_MUNICIPAL_ADMIN)) {
            user.setRole(Role.ROLE_MUNICIPAL_ADMIN);
            user.setMunicipality(municipality);
        }

        userRepository.save(user);
    }

    public void demoteMunicipalAdminToUser(Long userId) {

        User user = userService.findById(userId);

        if(user.getRole().equals(Role.ROLE_MUNICIPAL_ADMIN)) {
            user.setRole(Role.ROLE_USER);
            user.setMunicipality(null);
        }

        userRepository.save(user);
    }

    public AdminUserResponse findByUsername(String username) {
        User user = userService.findByUsername(username);

        Municipality municipality = user.getMunicipality();

        return new AdminUserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                municipality != null ? municipality.getId() : null,
                municipality != null ? municipality.getName() : null
        );
    }


}
