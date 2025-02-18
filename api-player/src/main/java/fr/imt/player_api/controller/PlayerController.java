package fr.imt.player_api.controller;

import fr.imt.player_api.model.PlayerModel;
import fr.imt.player_api.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @PostMapping("/new")
    public ResponseEntity<PlayerModel> createPlayer(@RequestBody PlayerModel player) {
        PlayerModel createdPlayer = playerService.createPlayer(player);
        return ResponseEntity.ok(createdPlayer);
    }
    @GetMapping("/list")
    public ResponseEntity<List<PlayerModel>> listPlayers(){
        List<PlayerModel> players = playerService.listPlayers();
        return ResponseEntity.ok(players);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<PlayerModel>> getPlayer(@PathVariable String id){
        Optional<PlayerModel> player = playerService.getPlayer(id);
        return ResponseEntity.ok(player);
    }

    @GetMapping("/{id}/level")
    public ResponseEntity<Integer> getPlayerLevel(@PathVariable String id){
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
    public ResponseEntity<PlayerModel> gainExp(@PathVariable String id, @RequestBody int exp) {
        PlayerModel player = playerService.gainExp(id, exp);
        return ResponseEntity.ok(player);
    }

    //Lister les monstres du joueur
    @GetMapping("/{id}/listMonsters")
    public ResponseEntity<List<Object>> listMonstersOfPlayer(@PathVariable String id) {
        List<Object> monsters = playerService.listMonstersOfPlayer(id);
        return ResponseEntity.ok(monsters);
    }

    //add new monstre a la liste

    //suppression d'un monstre a la liste

}
