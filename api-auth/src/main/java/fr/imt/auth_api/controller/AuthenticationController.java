package fr.imt.auth_api.controller;

import fr.imt.auth_api.domain.AuthenticationValidationRequest;
import fr.imt.auth_api.domain.AuthenticationValidationResponse;
import fr.imt.auth_api.domain.auth.AuthenticationRequest;
import fr.imt.auth_api.domain.auth.AuthenticationResponse;
import fr.imt.auth_api.dto.AppUserDto;
import fr.imt.auth_api.service.AuthenticationService;
import fr.imt.auth_api.service.JwtService;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth-api/v1/auth")
@RequiredArgsConstructor
@RestController
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("register")
    public ResponseEntity<AuthenticationResponse> register (
            @RequestBody AppUserDto appUserDto
    ) {
        return ResponseEntity.ok(authenticationService.register(appUserDto));
    }

    @PostMapping("token")
    public ResponseEntity<AuthenticationResponse> getToken(
            @RequestBody AuthenticationRequest authenticationRequest
    ) {
        log.info("Appel du service en cours {}", authenticationRequest.getUsername());
        return ResponseEntity.ok(authenticationService.getToken(authenticationRequest));
    }

    @GetMapping("validate")
    public ResponseEntity<AuthenticationValidationResponse> validateToken(@RequestHeader String authorization) {
            log.info("validateToken with token {}", authorization);
            return ResponseEntity.ok(new AuthenticationValidationResponse(authenticationService.validateToken(authorization), authorization));
    }
}
