package fr.imt.auth_api.service;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final PrivateKey jwtPrivateKey;
    private final PublicKey jwtPublicKey;

    /**
     * Generate a JWT token without extra claims
     *
     * @param userDetails User
     * @return JWT token
     */
    public String generateToken(UserDetails userDetails) {
        //We call generateToken by passing empty extra claims
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Generates a JWT token
     *
     * @param extraClaims Extra claims
     * @param userDetails User
     * @return JWT token
     */
    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(jwtPrivateKey)
                .compact();
    }


    public String extractUsername(String token) throws Exception {
        JwtParser jwtParser = Jwts.parser()
                .verifyWith(jwtPublicKey)
                .build();
        try {
            Jwt<?, ?> jwt = jwtParser.parse(token);

            if (jwt.getPayload() instanceof Claims claims) {
                return claims.getSubject();
            }

            return null;
        } catch (Exception e) {
            throw new Exception("Could not verify JWT token integrity!", e);
        }
    }


}