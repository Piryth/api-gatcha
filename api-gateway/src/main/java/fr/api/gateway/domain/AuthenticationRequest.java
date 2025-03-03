package fr.api.gateway.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class AuthenticationRequest {

    @Email
    String email;
    @Size(min = 8)
    String password;
    @NotNull
    String username;
}
