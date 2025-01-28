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

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePlayer(@PathVariable String id) {
        String response = playerService.deletePlayer(id);
        return ResponseEntity.ok(response);
    }


   @PutMapping("/{id}")
   public ResponseEntity<String> updatePlayer(@PathVariable String id, @RequestBody PlayerModel player) {
        String response =  playerService.updatePlayer(id, player);
        return ResponseEntity.ok(response);
   }

}
