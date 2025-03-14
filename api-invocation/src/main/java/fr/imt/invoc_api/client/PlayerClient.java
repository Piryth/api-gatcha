package fr.imt.invoc_api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "api-player")
public interface PlayerClient {

    @PostMapping(value = "player-api/v1/players/{playerId}/monsters/{monsterId}")
    void addMonster(@PathVariable String playerId, @PathVariable String monsterId);

}
