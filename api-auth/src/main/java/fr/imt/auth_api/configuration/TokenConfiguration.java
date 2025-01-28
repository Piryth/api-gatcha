package fr.imt.auth_api.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class TokenConfiguration {

    @Value("${token.expiration}")
    private int expiration;

    @Bean
    public Duration expiration() {
        return Duration.ofMinutes(expiration);
    }
}
