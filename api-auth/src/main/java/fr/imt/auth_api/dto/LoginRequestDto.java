package fr.imt.auth_api.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(@NotBlank String username, @NotBlank String password) {
}
