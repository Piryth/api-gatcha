package fr.imt.auth_api.controller;

import fr.imt.auth_api.domain.AppUser;
import fr.imt.auth_api.domain.auth.AuthenticationRequest;
import fr.imt.auth_api.service.TokenService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth-api/v1/auth")
@RequiredArgsConstructor
@RestController
public class AuthenticationController {

    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<String> register(@RequestBody @Valid AuthenticationRequest authenticationRequest) {

        AppUser appUser = AppUser.builder()
                .email(authenticationRequest.getEmail())
                .password(passwordEncoder.encode(authenticationRequest.getPassword()))
                .username(authenticationRequest.getUsername())
                .role("USER")
                .build();

        String token = tokenService.issueToken(appUser);
        return ResponseEntity.ok(token);
    }

    public ResponseEntity<String> login(@RequestBody @NotNull String token) {

        if(tokenService.validateToken(token)) {
            return ResponseEntity.ok("OK");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
    }
}
