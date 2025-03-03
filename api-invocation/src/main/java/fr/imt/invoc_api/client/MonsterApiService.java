package fr.imt.invoc_api.client;

import fr.imt.invoc_api.model.Invocation;
import fr.imt.invoc_api.model.response.MonsterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;

@Service
public class MonsterApiService {
    private final WebClient monsterClient;

    @Autowired
    public MonsterApiService(WebClient monsterClient) {
        this.monsterClient = monsterClient;
    }

    public String createMonster(Invocation invocation) {
        return Objects.requireNonNull(monsterClient.post()
                        .uri("/monsters")
                        .bodyValue(invocation)
                        .retrieve()
                        .bodyToMono(MonsterResponse.class)
                        .block())
                        .getId();
    }
}
