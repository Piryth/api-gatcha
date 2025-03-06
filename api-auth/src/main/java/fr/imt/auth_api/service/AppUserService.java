package fr.imt.auth_api.service;

import fr.imt.auth_api.domain.AppUser;
import fr.imt.auth_api.dto.AuthenticationResponseDto;
import fr.imt.auth_api.dto.AppUserDto;
import fr.imt.auth_api.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final JwtService jwtService;

    /**
     * @param username the username identifying the user whose data is required.
     * @return The user details
     * @throws UsernameNotFoundException if user not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUserRepository.findAppUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found : " + username));
    }

    /**
     * Registers a user to the DB and generates a token
     * @param appUserDto    An appUser, with email, username and password
     * @return              An authentication response containing the token
     */
    public AuthenticationResponseDto register(AppUserDto appUserDto) {
        AppUser user = AppUser.builder()
                .username(appUserDto.username())
                .email(appUserDto.email())
                .password(new BCryptPasswordEncoder().encode(appUserDto.password()))
                .role("USER")
                .build();

        appUserRepository.save(user);
        // Token generation
        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponseDto.builder()
                .token(jwtToken)
                .build();
    }

    /**
     * Generates token given a created user
     * @param appUserDto a user
     * @return a generated token
     */
    public AuthenticationResponseDto logIn(AppUserDto appUserDto) {
        AppUser user = appUserRepository.findAppUserByUsername(appUserDto.username()).orElseThrow( () -> new UsernameNotFoundException("User not found"));
        // Token generation
        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponseDto.builder()
                .token(jwtToken)
                .build();
    }

}
