package fr.imt.player_api.controller;

import fr.imt.player_api.dto.GainExperienceRequestDto;
import fr.imt.player_api.model.PlayerModel;
import fr.imt.player_api.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/player-api/v1/players")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @PostMapping("/new")
    public ResponseEntity<PlayerModel> createPlayer(@RequestBody PlayerModel player) {
        PlayerModel createdPlayer = playerService.createPlayer(player);
        return ResponseEntity.ok(createdPlayer);
    }

    @GetMapping("/list")
    public ResponseEntity<List<PlayerModel>> listPlayers() {
        List<PlayerModel> players = playerService.listPlayers();
        return ResponseEntity.ok(players);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerModel> getPlayer(@PathVariable String id) {
        PlayerModel player = playerService.getPlayer(id);
        return ResponseEntity.ok(player);
    }

    @GetMapping("/{id}/level")
    public ResponseEntity<Integer> getPlayerLevel(@PathVariable String id) {
        int level = playerService.getLevelOfPlayer(id);
        return ResponseEntity.ok(level);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePlayer(@PathVariable String id) {
        String response = playerService.deletePlayer(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/levelUp")
    public ResponseEntity<PlayerModel> levelUp(@PathVariable String id) {
        PlayerModel player = playerService.levelUp(id);
        return ResponseEntity.ok(player);
    }

    @PostMapping("/{id}/gainExp")
    public ResponseEntity<PlayerModel> gainExp(@PathVariable String id, @RequestBody GainExperienceRequestDto gainExperienceRequestDto) {
        PlayerModel player = playerService.gainExp(id, gainExperienceRequestDto.experience());
        return ResponseEntity.ok(player);
    }

    //Lister les monstres du joueur
    @GetMapping("/{id}/listMonsters")
    public ResponseEntity<List<String>> listMonstersOfPlayer(@PathVariable String id) {
        List<String> monsters = playerService.listMonstersOfPlayer(id);
        return ResponseEntity.ok(monsters);
    }

    @PostMapping("/{playerId}/monsters/{monsterId}")
    public ResponseEntity<String> addMonsterToPlayer(@PathVariable String playerId, @PathVariable String monsterId) {
        String res = playerService.addMonsterToPlayer(playerId, monsterId);
        return ResponseEntity.ok(res);
    }

}
