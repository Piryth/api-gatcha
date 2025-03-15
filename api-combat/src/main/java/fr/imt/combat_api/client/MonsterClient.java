package fr.imt.combat_api.client;

import fr.imt.combat_api.model.response.MonsterResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "api-monster")
public interface MonsterClient {

    @GetMapping(value = "/monster-api/v1/monsters/{monsterId}")
    MonsterResponse getMonster(@PathVariable String monsterId);

}
