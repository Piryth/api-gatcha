package fr.imt.gateway.service;

import fr.imt.gateway.exception.JwtTokenMalformedException;
import fr.imt.gateway.exception.JwtTokenMissingException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.security.PublicKey;

@Service
@RequiredArgsConstructor
public class JwtService {

    private PublicKey jwtPublicKey;

    /**
     * Validates a token
     * @param token     Jwt token to be validated
     * @return          A mono for filter chain
     * @throws JwtTokenMalformedException   If token is invalid
     * @throws JwtTokenMissingException     If token is missing
     */
    public Mono<Void> validateToken(final String token) throws JwtTokenMalformedException, JwtTokenMissingException {
        try {
            Jwts.parser().verifyWith(jwtPublicKey).build().parseSignedClaims(token);
            return Mono.empty();
        } catch (MalformedJwtException ex) {
            return Mono.error(new JwtTokenMalformedException("Invalid JWT token"));
        } catch (ExpiredJwtException ex) {
            return Mono.error(new JwtTokenMalformedException("Expired JWT token"));
        } catch (UnsupportedJwtException ex) {
            return Mono.error(new JwtTokenMalformedException("Unsupported JWT token"));
        } catch (IllegalArgumentException ex) {
            return Mono.error(new JwtTokenMalformedException("JWT claims string is empty."));
        } catch (SignatureException ex) {
            return Mono.error(new JwtTokenMalformedException("Jwt signature does not match"));
        }
    }

}

