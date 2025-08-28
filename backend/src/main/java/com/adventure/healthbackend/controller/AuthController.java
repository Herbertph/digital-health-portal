package com.adventure.healthbackend.controller;

import com.adventure.healthbackend.dto.*;
import com.adventure.healthbackend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService auth;
    public AuthController(AuthService auth) { this.auth = auth; }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody RegisterRequest req) {
        return ResponseEntity.ok(auth.register(req));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest req) {
        boolean ok = auth.validateLogin(req); // por enquanto só valida; JWT vem depois
        return ResponseEntity.ok(new LoginResponse(ok));
    }
}
