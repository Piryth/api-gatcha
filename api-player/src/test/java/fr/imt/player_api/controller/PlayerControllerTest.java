package fr.imt.player_api.controller;

import fr.imt.player_api.dto.GainExperienceRequestDto;
import fr.imt.player_api.model.PlayerModel;
import fr.imt.player_api.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PlayerControllerTest {

    @Mock
    private PlayerService playerService;

    @InjectMocks
    private PlayerController playerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPlayerReturnsCreatedPlayer() {
        List<String> monsters = Arrays.asList("monster1", "monster2");
        PlayerModel player = new PlayerModel(1, null, "Player1");
        when(playerService.createPlayer(player)).thenReturn(player);

        PlayerModel result = playerController.createPlayer(player).getBody();

        assertEquals(player, result);
    }

    @Test
    void listPlayersReturnsListOfPlayers() {
        List<String> monsters = Arrays.asList("monster1", "monster2");
        List<PlayerModel> players = Arrays.asList(
                new PlayerModel(1, null, "Player1"),
                new PlayerModel(1, null, "Player2")
        );
        when(playerService.listPlayers()).thenReturn(players);

        List<PlayerModel> result = playerController.listPlayers().getBody();

        assertEquals(players, result);
    }

    @Test
    void getPlayerReturnsPlayerById() {
        List<String> monsters = Arrays.asList("monster1", "monster2");
        PlayerModel player = new PlayerModel(1, null, "Player1");
        when(playerService.getPlayer("1")).thenReturn(player);
        ResponseEntity<PlayerModel> response = playerController.getPlayer("1");
        assertEquals(player, response.getBody());
    }

    @Test
    void getPlayerLevelReturnsPlayerLevel() {
        when(playerService.getLevelOfPlayer("1")).thenReturn(5);
        ResponseEntity<Integer> response = playerController.getPlayerLevel("1");
        assertEquals(5, response.getBody());
    }

    @Test
    void deletePlayerReturnsOkResponse() {
        String id = "1";
        when(playerService.deletePlayer(id)).thenReturn("Player deleted successfully");

        ResponseEntity<String> response = playerController.deletePlayer(id);

        assertEquals(ResponseEntity.ok("Player deleted successfully"), response);
    }

    @Test
    void levelUpReturnsUpdatedPlayer() {
        List<String> monsters = Arrays.asList("monster1", "monster2");
        PlayerModel player = new PlayerModel(1, null, "Player1");
        when(playerService.levelUp("1")).thenReturn(player);

        PlayerModel result = playerController.levelUp("1").getBody();

        assertEquals(player, result);
    }

    @Test
    void gainExpReturnsUpdatedPlayer() {
        List<String> monsters = Arrays.asList("monster1", "monster2");
        PlayerModel player = new PlayerModel(1, null, "Player1");
        when(playerService.gainExp("1", 10)).thenReturn(player);
        PlayerModel result = playerController.gainExp("1", new GainExperienceRequestDto(10)).getBody();
        assertEquals(player, result);
    }


    @Test
    void listMonstersOfPlayerReturnsMonsters() {
        List<String> monsters = Arrays.asList("monster1", "monster2");
        when(playerService.listMonstersOfPlayer("1")).thenReturn(monsters);

        ResponseEntity<List<String>> response = playerController.listMonstersOfPlayer("1");

        assertEquals(monsters, response.getBody());
    }

    @Test
    void addMonsterToPlayerReturnsSuccessMessage() {
        String playerId = "1";
        String monsterId = "monster1";
        when(playerService.addMonsterToPlayer(playerId, monsterId)).thenReturn("Monster added successfully");

        ResponseEntity<String> response = playerController.addMonsterToPlayer(playerId, monsterId);

        assertEquals("Monster added successfully", response.getBody());
    }
}
