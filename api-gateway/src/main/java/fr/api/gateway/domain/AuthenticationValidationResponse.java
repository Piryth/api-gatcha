package fr.api.gateway.domain;

public record AuthenticationValidationResponse(boolean isValid, String token) {
}
