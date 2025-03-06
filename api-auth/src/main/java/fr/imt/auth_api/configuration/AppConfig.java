package fr.imt.auth_api.configuration;

import fr.imt.auth_api.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

    private final AppUserService appUserService;

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(List.of(new DaoAuthenticationProvider() {{
            setUserDetailsService(appUserService);
            setPasswordEncoder(new BCryptPasswordEncoder());
        }}));
    }

}