package fr.imt.invoc_api.client;

import fr.imt.invoc_api.model.Invocation;
import fr.imt.invoc_api.model.response.MonsterResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "api-monster")
public interface MonsterClient {

    @PostMapping(value = "/monster-api/v1/monsters/new")
    MonsterResponse createMonster(Invocation invocation);

}
