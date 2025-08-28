package com.adventure.healthbackend.service;

import com.adventure.healthbackend.domain.Role;
import com.adventure.healthbackend.domain.User;
import com.adventure.healthbackend.dto.*;
import com.adventure.healthbackend.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository users;
    private final PasswordEncoder encoder = new BCryptPasswordEncoder(); // simples por enquanto

    public AuthService(UserRepository users) {
        this.users = users;
    }

    @Transactional
    public UserDto register(RegisterRequest req) {
        if (users.existsByUsername(req.username())) {
            throw new IllegalArgumentException("username already in use");
        }
        if (users.existsByEmail(req.email())) {
            throw new IllegalArgumentException("email already in use");
        }

        var user = new User(
                req.username(),
                req.email(),
                encoder.encode(req.password()), // regra: sempre hash
                Role.PATIENT // padrão
        );
        var saved = users.save(user);
        return new UserDto(saved.getId(), saved.getUsername(), saved.getEmail(), saved.getRole().name());
    }

    public boolean validateLogin(LoginRequest req) {
        var user = users.findByUsername(req.username())
                .orElseThrow(() -> new IllegalArgumentException("invalid credentials"));
        if (!encoder.matches(req.password(), user.getPasswordHash())) {
            throw new IllegalArgumentException("invalid credentials");
        }
        return true; // no próximo passo retorna JWT
    }

    public UserDto toDto(User u) {
        return new UserDto(u.getId(), u.getUsername(), u.getEmail(), u.getRole().name());
    }
}
