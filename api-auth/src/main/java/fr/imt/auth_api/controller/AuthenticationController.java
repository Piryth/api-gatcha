package fr.imt.auth_api.controller;

import fr.imt.auth_api.domain.AppUser;
import fr.imt.auth_api.dto.AuthenticationResponseDto;
import fr.imt.auth_api.dto.RegisterRequestDto;
import fr.imt.auth_api.dto.LoginRequestDto;
import fr.imt.auth_api.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth-api/v1/auth")
@RequiredArgsConstructor
@RestController
public class AuthenticationController {

    private final AppUserService appUserService;

    @PostMapping("register")
    public ResponseEntity<AuthenticationResponseDto> register (
            @RequestBody RegisterRequestDto registerRequestDto
    ) {
        return ResponseEntity.ok(appUserService.register(registerRequestDto));
    }

    @PostMapping("login")
    public ResponseEntity<AuthenticationResponseDto> login(
            @RequestBody LoginRequestDto loginRequestDto
    ) {
        return ResponseEntity.ok(appUserService.logIn(loginRequestDto));
    }

    @GetMapping("me")
    public ResponseEntity<AppUser> getConnectedUser(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(appUserService.getConnectedUser(token.substring(7)));
    }


}
