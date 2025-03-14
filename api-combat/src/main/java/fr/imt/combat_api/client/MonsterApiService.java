package fr.imt.combat_api.client;

import fr.imt.combat_api.model.response.MonsterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class MonsterApiService {
    private final WebClient monsterClient;

    @Autowired
    public MonsterApiService(WebClient monsterClient) {
        this.monsterClient = monsterClient;
    }

    public Mono<MonsterResponse> getMonster(String id) {
        return monsterClient.get()
                .uri("/monsters/{id}", id)
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        _ -> Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Monstre non trouvé"))
                )
                .onStatus(
                        HttpStatusCode::is5xxServerError,
                        _ -> Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur serveur"))
                )
                .bodyToMono(MonsterResponse.class)
                .timeout(Duration.ofSeconds(5))
                .doOnError(error -> System.out.println("Erreur lors de la récupération du monstre : " + error.getMessage()));
    }
}
