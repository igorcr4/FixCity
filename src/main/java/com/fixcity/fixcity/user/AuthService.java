package com.fixcity.fixcity.user;

import com.fixcity.fixcity.user.exception.IncorrectPasswordException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public User authenticate(String email, String password) {
        User user = userService.findByEmail(email);

        if(passwordEncoder.matches(password, user.getPassword())) {
            return user;
        } else {
            throw new IncorrectPasswordException();
        }
    }
}
