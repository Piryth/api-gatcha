package fr.imt.invoc_api.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class PlayerApiService {

    private final WebClient playerClient;

    @Autowired
    public PlayerApiService(WebClient playerClient) {
        this.playerClient = playerClient;
    }

    public void addMonster(String playerId, String monsterId) {
        playerClient.post()
                .uri("/players/{playerId}/monsters/{monsterId}", playerId, monsterId)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

}
