package fr.imt.auth_api.service;

import fr.imt.auth_api.domain.AppUser;
import fr.imt.auth_api.dto.AppUserDto;
import fr.imt.auth_api.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    /**
     * @param username the username identifying the user whose data is required.
     * @return The user details
     * @throws UsernameNotFoundException if user not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> optUser = appUserRepository.findAppUserByUsername(username);
        log.info("User {} found", optUser.toString());
        return appUserRepository.findAppUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found : " + username));
    }

    public AppUserDto getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        AppUser user = appUserRepository.findAppUserByUsername(username).orElseThrow();
        return new AppUserDto(username, user.getEmail(), null);
    }

    /**
     * Retrives the authenticated user and updates its credentials
     *
     * @param userDto user
     * @return userDto user
     */
    public AppUserDto updateAuthenticatedUser(AppUserDto userDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        AppUser user = appUserRepository.findAppUserByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found"));
        user.setUsername(userDto.username());
        user.setEmail(userDto.email());
        System.out.println(new BCryptPasswordEncoder().encode(userDto.password()));
        user.setPassword(new BCryptPasswordEncoder().encode(userDto.password()));
        appUserRepository.save(user);
        return userDto;
    }

    public boolean deleteAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        AppUser user = appUserRepository.findAppUserByUsername(username).orElseThrow();
        appUserRepository.delete(user);
        return true;
    }
}
