package fr.imt.auth_api;

import fr.imt.auth_api.domain.AppUser;
import fr.imt.auth_api.repository.AppUserRepository;
import fr.imt.auth_api.repository.TokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@ActiveProfiles("test")
@SpringBootTest
@Testcontainers
public class TokenServiceIT {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0").withExposedPorts(27017);

    private final AppUserRepository appUserRepository;

    @Autowired
    TokenServiceIT(AppUserRepository appUserRepository) {
        this.appUserRepository= appUserRepository;
    }

    @DynamicPropertySource
    static void containersProperties(DynamicPropertyRegistry registry) {
        mongoDBContainer.start();
        registry.add("spring.data.mongodb.host", mongoDBContainer::getHost);
        registry.add("spring.data.mongodb.port", mongoDBContainer::getFirstMappedPort);
    }

    @BeforeEach
    void setUp() {
        appUserRepository.deleteAll();
    }

    void givenAppUser_whenValidatingToken_shouldGrantUser() {
        // GIVEN
        AppUser appUser = AppUser.builder()
                .email("test@tect.com")
                .role("USER")
                .password("azerty")
                .username("test")
                .build();
        appUserRepository.save(appUser);

        // WHEN


    }
}
