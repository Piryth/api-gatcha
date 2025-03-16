package fr.imt.auth_api.dto;

import fr.imt.auth_api.domain.AppUser;
import lombok.*;

@Builder
public record AuthenticationResponseDto(String token, AppUser user) {}