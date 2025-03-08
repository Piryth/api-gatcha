package fr.imt.combat_api.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient monsterClient(WebClient.Builder builder) {
        return builder.baseUrl("http://localhost:8083").build();
    }

}
