package fr.imt.auth_api.service;

import fr.imt.auth_api.domain.AppUser;
import fr.imt.auth_api.dto.AuthenticationResponseDto;
import fr.imt.auth_api.dto.RegisterRequestDto;
import fr.imt.auth_api.dto.LoginRequestDto;
import fr.imt.auth_api.exception.UserAlreadyExistsException;
import fr.imt.auth_api.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
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
     *
     * @param registerRequestDto An appUser, with email, username and password
     * @return An authentication response containing the token
     */
    public AuthenticationResponseDto register(RegisterRequestDto registerRequestDto) {
        appUserRepository.findAppUserByUsername(registerRequestDto.username()).ifPresent(_ -> {
            throw new UserAlreadyExistsException("An user with same email or username already exists.");
        });

        log.info("Registering user with username {}", registerRequestDto.username());
        AppUser user = AppUser.builder()
                .username(registerRequestDto.username())
                .email(registerRequestDto.email())
                .password(new BCryptPasswordEncoder().encode(registerRequestDto.password()))
                .role("USER")
                .build();

        appUserRepository.save(user);
        // Token generation
        log.info("Generating token for user {}", registerRequestDto.username());
        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponseDto.builder()
                .token(jwtToken)
                .build();
    }

    /**
     * Generates token given a created user
     *
     * @param loginRequestDto a username and password
     * @return a generated token
     */
    public AuthenticationResponseDto logIn(LoginRequestDto loginRequestDto) {
        AppUser user = appUserRepository.findAppUserByUsername(loginRequestDto.username()).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (passwordEncoder.matches(loginRequestDto.password(), user.getPassword())) {
            // Token generation
            String jwtToken = jwtService.generateToken(user);
            log.info("Logged in user {}", loginRequestDto.username());
            return AuthenticationResponseDto.builder()
                    .token(jwtToken)
                    .build();
        }
        log.error("Invalid credentials");
        throw new BadCredentialsException("Incorrect username or password");

    }

}
