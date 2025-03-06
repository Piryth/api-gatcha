package fr.api.gateway.configuration;

import fr.api.gateway.exception.JwtTokenMalformedException;
import fr.api.gateway.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Slf4j
@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final RouteValidator routeValidator;

    private final JwtService jwtService;

    public AuthenticationFilter(RouteValidator routeValidator, JwtService jwtService) {
        super(Config.class);
        this.routeValidator = routeValidator;
        this.jwtService = jwtService;
    }

    public static class Config {
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            // If secured route
            // Token verification
            if (routeValidator.isSecured.test(exchange.getRequest())) {
                return this.filter(exchange, chain);
            }
            // If non-secured route, forward request
            return chain.filter(exchange);
        });
    }

    private Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // Extracting JWT from header
        HttpHeaders headers = exchange.getRequest().getHeaders();
        // We first check for Authorization header
        if (!headers.containsKey(HttpHeaders.AUTHORIZATION)) {
            // If not present, return 401
            return this.onError(exchange, "Authorization header not found", HttpStatus.UNAUTHORIZED);
        }
        // Extracting header
        String authHeaders = Objects.requireNonNull(exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION)).get(0);
        if (authHeaders != null && authHeaders.startsWith("Bearer ")) {
            String token = authHeaders.substring(7);
            return this.validate(exchange, chain, token);
        }
        // Authorization header is not bearer, return 401
        return this.onError(exchange, "Invalid authentication method", HttpStatus.UNAUTHORIZED);
    }

    private Mono<Void> validate(ServerWebExchange exchange, GatewayFilterChain chain, final String token) {
        // Validate token asynchronously
        return jwtService.validateToken(token)
                // If successful, forward request
                .then(chain.filter(exchange))
                // Handle Jwt based errors
                .onErrorResume(JwtTokenMalformedException.class,
                        ex -> Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage())));
    }

    private Mono<Void> onError(ServerWebExchange exchange, String message, HttpStatus httpStatus) {
        log.error(message);
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }
}
