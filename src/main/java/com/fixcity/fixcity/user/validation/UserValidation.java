package com.fixcity.fixcity.user.validation;

import com.fixcity.fixcity.user.repository.UserRepository;
import com.fixcity.fixcity.user.exception.WeakPasswordException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidation {
    private final UserRepository userRepository;

    String emailRegex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)" +
            "(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?])\\S{8,}$";

    public void validateEmail(String email) {
        if(email == null || userRepository.existsByEmail(email) || !email.matches(emailRegex)) {
            throw new RuntimeException("Email: " + email + " este invalid sau deja exista!");
        }
    }

    public void validateUsername(String username) {
        if(username == null || userRepository.existsByUsername(username)) {
            throw new RuntimeException("Email: " + username + " este invalid sau deja exista!");
        }
    }

    public void validatePassword(String password) {
        if(password == null || !password.matches(passwordRegex)) {
            throw new WeakPasswordException();
        }
    }
}
