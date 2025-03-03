package fr.imt.auth_api.domain;

public record AuthenticationValidationResponse(boolean isValid, String token) {
}
