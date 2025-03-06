package fr.imt.auth_api.controller;

import fr.imt.auth_api.dto.AuthenticationResponseDto;
import fr.imt.auth_api.dto.AppUserDto;
import fr.imt.auth_api.service.AppUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth-api/v1/auth")
@RequiredArgsConstructor
@RestController
@Slf4j
public class AuthenticationController {

    private final AppUserService appUserService;

    @PostMapping("register")
    public ResponseEntity<AuthenticationResponseDto> register (
            @RequestBody AppUserDto appUserDto
    ) {
        log.info("Registering new user {}", appUserDto.username());
        return ResponseEntity.ok(appUserService.register(appUserDto));
    }

    @PostMapping("login")
    public ResponseEntity<AuthenticationResponseDto> login(
            @RequestBody AppUserDto appUserDto
    ) {
        log.info("Logging in user {}", appUserDto.username());
        return ResponseEntity.ok(appUserService.logIn(appUserDto));
    }

}
