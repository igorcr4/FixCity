package com.fixcity.fixcity.user.service;

import com.fixcity.fixcity.user.model.User;
import com.fixcity.fixcity.user.repository.UserRepository;
import com.fixcity.fixcity.user.exception.EmailNotFound;
import com.fixcity.fixcity.user.exception.IncorrectPasswordException;
import com.fixcity.fixcity.user.exception.UsernameNotFound;
import com.fixcity.fixcity.user.validation.UserValidation;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserValidation userValidation;

    public void registerUser(String username, String email, String password) {
        User user = new User();

        userValidation.validateUsername(username);
        user.setUsername(username);

        userValidation.validateEmail(email);
        user.setEmail(email);

        userValidation.validatePassword(password);
        user.setPassword(passwordEncoder.encode(password));

        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(UsernameNotFound::new);
    }

    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(EmailNotFound::new);
    }

    @Transactional
    public void changePassword(String username, String oldPassword, String newPassword) {

        User user = findByUsername(username);

        if (passwordEncoder.matches(oldPassword, user.getPassword()) && !passwordEncoder.matches(newPassword, oldPassword)) {

            userValidation.validatePassword(newPassword);
            passwordEncoder.encode(newPassword);

            user.setPassword(newPassword);
        } else {
            throw new IncorrectPasswordException();
        }
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(UsernameNotFound::new);
    }

    public User getReference(Long userId) {
       return userRepository.getReferenceById(userId);
    }

}
