package fr.imt.player_api.controller;

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
        PlayerModel player = new PlayerModel(1, 50, 0, monsters, "Player1");
        when(playerService.createPlayer(player)).thenReturn(player);

        PlayerModel result = playerController.createPlayer(player).getBody();

        assertEquals(player, result);
    }

    @Test
    void listPlayersReturnsListOfPlayers() {
        List<String> monsters = Arrays.asList("monster1", "monster2");
        List<PlayerModel> players = Arrays.asList(
                new PlayerModel(1, 50, 0, monsters, "Player1"),
                new PlayerModel(2, 100, 0, monsters, "Player2")
        );
        when(playerService.listPlayers()).thenReturn(players);

        List<PlayerModel> result = playerController.listPlayers().getBody();

        assertEquals(players, result);
    }

    @Test
    void getPlayerReturnsPlayerById() {
        List<String> monsters = Arrays.asList("monster1", "monster2");
        PlayerModel player = new PlayerModel(1, 50, 0, monsters, "Player1");
        when(playerService.getPlayer("1")).thenReturn(Optional.of(player));

        PlayerModel result = playerController.getPlayer("1").getBody().get();

        assertEquals(player, result);
    }

    @Test
    void getPlayerReturnsNullWhenNoPlayer() {
        when(playerService.getPlayer("1")).thenReturn(Optional.empty());

        Optional<PlayerModel> result = playerController.getPlayer("1").getBody();

        assertEquals(Optional.empty(), result);
    }

    @Test
    void getPlayerLevelReturnsPlayerLevel() {
        when(playerService.getLevelOfPlayer("1")).thenReturn(5);

        int result = playerController.getPlayerLevel("1").getBody();

        assertEquals(5, result);
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
        PlayerModel player = new PlayerModel(1, 50, 0, monsters, "Player1");
        when(playerService.levelUp("1")).thenReturn(player);

        PlayerModel result = playerController.levelUp("1").getBody();

        assertEquals(player, result);
    }

    @Test
    void gainExpReturnsUpdatedPlayer() {
        List<String> monsters = Arrays.asList("monster1", "monster2");
        PlayerModel player = new PlayerModel(1, 50, 0, monsters, "Player1");
        when(playerService.gainExp("1", 10)).thenReturn(player);

        PlayerModel result = playerController.gainExp("1", 10).getBody();

        assertEquals(player, result);
    }

    @Test
    void listMonstersOfPlayerReturnsMonsters() {
       //TODO
    }

    @Test
    void invalidPlayerConfigurationReturnsTrueForInvalidConfiguration() {
        List<String> monsters = Arrays.asList("monster1", "monster2");
        PlayerModel player = new PlayerModel(1, 50, 0, monsters, "Player1");
        player.setLevel(1);
        player.setExp(50);
        player.setCurr_exp(0);

        boolean result = player.invalidPlayerConfiguration();

        // Assuming the invalid configuration is for the level or exp mismatch
        assertEquals(true, result);
    }


    @Test
    void increaseNecessaryExpForLevelUpReturnsCorrectExp() {
        List<String> monsters = Arrays.asList("monster1", "monster2");
        PlayerModel player = new PlayerModel(1, 50, 0, monsters, "Player1");
        player.increaseNecessaryExpForLevelUp();

        assertEquals(50, player.getExp());
    }
}
