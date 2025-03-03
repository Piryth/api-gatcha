package fr.imt.invoc_api.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient monsterClient(WebClient.Builder builder) {
        return builder.baseUrl("http://localhost:8080").build();
    }

    @Bean
    public WebClient playerClient(WebClient.Builder builder) {
        return builder.baseUrl("http://localhost:8081").build();
    }

}
