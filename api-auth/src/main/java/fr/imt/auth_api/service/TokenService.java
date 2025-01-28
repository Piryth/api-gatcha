package fr.imt.auth_api.service;

import fr.imt.auth_api.domain.AppUser;
import fr.imt.auth_api.domain.Token;
import fr.imt.auth_api.repository.AppUserRepository;
import fr.imt.auth_api.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final Duration expiration;

    private final AppUserRepository appUserRepository;
    private final TokenRepository tokenRepository;

    public String issueToken(AppUser user) {
        LocalDateTime expires = LocalDateTime.now().plus(expiration);

        String tokenString = user.getEmail() + expires.toString();
        byte[] bytes = tokenString.getBytes();

        Token token = Token.builder()
                .expires(expires)
                .token(Base64.getEncoder().encodeToString(bytes))
                .build();

        tokenRepository.save(token);

        return token.getToken();
    }

    public boolean validateToken(String tokenString) {
        Optional<Token> tokenOptional = tokenRepository.findByToken(tokenString);
        return tokenOptional.isPresent() && tokenOptional.get().getExpires().isBefore(LocalDateTime.now());
    }


}
