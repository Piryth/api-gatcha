package fr.api.gateway.utils;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.experimental.UtilityClass;

import javax.crypto.SecretKey;

@UtilityClass
public class JwtUtils{

    private final static String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970s";

    private SecretKey getSignInKey() {
        byte[] keyByte = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyByte);
    }

}