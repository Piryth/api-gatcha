package fr.api.gateway.service;

import fr.api.gateway.exception.JwtTokenMalformedException;
import fr.api.gateway.exception.JwtTokenMissingException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;

@Service
public class JwtService {

    @Value("${jwt-secret}")
    private String secretKey;

    /**
     * Validates a token
     * @param token     Jwt token to be validated
     * @return          A mono for filter chain
     * @throws JwtTokenMalformedException   If token is invalid
     * @throws JwtTokenMissingException     If token is missing
     */
    public Mono<Void> validateToken(final String token) throws JwtTokenMalformedException, JwtTokenMissingException {
        try {
            Jwts.parser().verifyWith(getSignInKey()).build().parseSignedClaims(token);
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

    private SecretKey getSignInKey() {
        byte[] keyByte = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyByte);
    }

}

