package fr.api.gateway.configuration;

import fr.api.gateway.client.AuthenticationClient;
import fr.api.gateway.client.AuthenticationWebClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import java.util.Objects;

@Slf4j
@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final RouteValidator routeValidator;

    private final AuthenticationWebClient authenticationWebClient;

   public AuthenticationFilter(RouteValidator routeValidator, AuthenticationWebClient authenticationWebClient) {
       super(Config.class);
       this.routeValidator = routeValidator;
       this.authenticationWebClient = authenticationWebClient;
   }

    public static class Config {}

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if(routeValidator.isSecured.test(exchange.getRequest())) {
                HttpHeaders headers = exchange.getRequest().getHeaders();
                if (!headers.containsKey(HttpHeaders.AUTHORIZATION)) {
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete(); // Stop processing
                }
                String authHeaders = Objects.requireNonNull(exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION)).getFirst();
                if(authHeaders != null && authHeaders.startsWith("Bearer ")) {
                    String token = authHeaders.substring(7);

                    // Validate token asynchronously
                        return authenticationWebClient.validateToken(token)
                            .flatMap(response -> {
                                if (response.isValid()) {
                                    return chain.filter(exchange);
                                }
                                log.info("Token validation failed: Unauthorized (401)");
                                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                                return exchange.getResponse().setComplete();

                            })
                            .then(chain.filter(exchange)) // If successful, forward request
                            .onErrorResume(e -> {
                                log.error("Unexpected error during token validation", e);
                                exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                                return exchange.getResponse().setComplete();
                            });
                } else {
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                }
            }
            return chain.filter(exchange);
        });
    }
}
