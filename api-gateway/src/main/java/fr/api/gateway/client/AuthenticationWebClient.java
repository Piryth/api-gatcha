package fr.api.gateway.client;

import fr.api.gateway.domain.AuthenticationValidationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AuthenticationWebClient {

    private final WebClient webClient;

    public AuthenticationWebClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("lb://api-authentication").build();
    }

    public Mono<AuthenticationValidationResponse> validateToken(String token) {
        log.info("Validating token {} webclient", token);
        return webClient.get()
                .uri("api/auth/validate")
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(AuthenticationValidationResponse.class);
    }
}
