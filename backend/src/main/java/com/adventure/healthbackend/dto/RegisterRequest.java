package com.adventure.healthbackend.dto;

import jakarta.validation.constraints.*;

public record RegisterRequest(
        @NotBlank @Size(min=3, max=50) String username,
        @NotBlank @Email @Size(max=120) String email,
        @NotBlank @Size(min=9, max=100) String password
) {}
