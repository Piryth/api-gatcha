package fr.api.gateway.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtils {

    private final static String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970s";

    /**
     * Extracts the username from a JWT token
     *
     * @param jwtToken JWT token
     *
     * @return Extracted username
     */
    public String extractUsername(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);
    }

    /**
     * Extracts a claim from a jwt token
     *
     * @param jwtToken The token to extract
     * @param claimsResolver The claim to extract
     * @return  A claim
     * @param <T> type
     */
    public <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(jwtToken);
        return claimsResolver.apply(claims);
    }

    /**
     * Generate a JWT token without extra claims
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
     * @param extraClaims   Extra claims
     * @param userDetails   User
     * @return JWT token
     */
    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return Jwts
                .builder().claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignInKey())
                .compact();
    }

    /**
     * Checks whether a token is valid. Checks if username is valid.
     * @param jwtToken  The token to validate
     * @param userDetails The user to check
     * @return a boolean
     */
    public boolean isTokenValid(String jwtToken, UserDetails userDetails) {
        final String username = extractUsername(jwtToken);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken);
    }

    private boolean isTokenExpired(String jwtToken) {
        return extractExpiration(jwtToken).before(new Date());
    }

    /**
     * Checks whether the token has expired
     * @param jwtToken  The JWT token
     * @return  The expiration date
     */
    private Date extractExpiration(String jwtToken) {
        //We extract the expiration claim from the token
        return extractClaim(jwtToken, Claims::getExpiration);
    }

    /**
     * Extracts all the claims of the token in a list
     * @param jwtToken  JWT token
     * @return A list of claims (Claims<T,V> class is extended from Map<T,V>)
     */
    private Claims extractAllClaims(String jwtToken) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(jwtToken)
                .getPayload();
    }

    private SecretKey getSignInKey() {
        byte[] keyByte = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyByte);
    }
}