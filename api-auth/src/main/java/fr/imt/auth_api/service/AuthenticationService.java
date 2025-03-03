package fr.imt.auth_api.service;

import fr.imt.auth_api.domain.AppUser;
import fr.imt.auth_api.domain.auth.AuthenticationRequest;
import fr.imt.auth_api.domain.auth.AuthenticationResponse;
import fr.imt.auth_api.dto.AppUserDto;
import fr.imt.auth_api.repository.AppUserRepository;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final static Logger logger = Logger.getLogger(AuthenticationService.class.getName());

    private final AppUserRepository appUserRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(AppUserDto appUserDto) {
        //Création de l'utilisateur
        AppUser user = AppUser.builder()
                .username(appUserDto.username())
                .email(appUserDto.email())
                .password(new BCryptPasswordEncoder().encode(appUserDto.password()))
                .role("USER")
                .build();
        //Sauvegarde de l'utilisateur
        appUserRepository.save(user);
        //Génération du token
        String jwtToken = jwtService.generateToken(user);
        //Génération de la réponse
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse getToken(AuthenticationRequest authenticationRequest) {
        logger.log( Level.INFO,"Authentification de l'utilisateur : " + authenticationRequest.getUsername());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );
        AppUser user = appUserRepository.findAppUserByUsername(authenticationRequest.getUsername()).orElseThrow( () -> new UsernameNotFoundException("User not found"));
        //Génération du token
        String jwtToken = jwtService.generateToken(user);
        //Génération de la réponse
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public boolean validateToken(String jwtToken) {
        // Retrieves the user from database and checks if present
        try {
            String username = jwtService.extractUsername(jwtToken);
            log.info("User to get is {}", jwtService.extractUsername(jwtToken));
            Optional<AppUser> appUserOptional = appUserRepository.findAppUserByUsername(username);
            if(appUserOptional.isPresent()) {
                log.info("Found user {}", username);
                return jwtService.isTokenValid(jwtToken, appUserOptional.get());
            }
            log.info("No user corresponding to username {}", username);
            return false;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token", e);
            return false;
        }
    }

}