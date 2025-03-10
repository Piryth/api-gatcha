package fr.imt.auth_api.dto;

import lombok.*;

@Builder
public record AuthenticationResponseDto(String token) {}