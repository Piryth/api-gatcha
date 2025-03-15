package fr.imt.gateway.configuration;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    public static final List<String> openedEndpoints = List.of(
            "auth/register",
            "auth/login",
            "v3/api-docs",
            "swagger-ui.html",
            "swagger-ui/"
    );

    public Predicate<ServerHttpRequest> isSecured = request -> openedEndpoints.stream()
            .noneMatch(uri -> request.getURI().getPath().contains(uri));

}
