package fr.imt.player_api.service;

import fr.imt.player_api.model.PlayerModel;
import fr.imt.player_api.repository.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/*
class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerService playerService;

    private PlayerModel player;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        player = new PlayerModel(1, 50, 0, Arrays.asList("monster1", "monster2"), "Player1");
    }

    @Test
    void createPlayerReturnsCreatedPlayer() {
        // Exemple de joueur avec des données valides
        List<String> monsters = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            monsters.add("Monster" + i);
        }

        PlayerModel validPlayer = new PlayerModel(1, 50, 0, monsters, "TestPlayer");

        // Simuler le comportement du repository
        when(playerRepository.save(any(PlayerModel.class))).thenReturn(validPlayer);

        // Créer le joueur
        PlayerModel result = playerService.createPlayer(validPlayer);

        // Vérifier que le joueur créé est bien celui attendu
        assertEquals(validPlayer, result);
    }


    @Test
    void listPlayersReturnsListOfPlayers() {
        List<PlayerModel> players = Arrays.asList(player, new PlayerModel(2, 100, 0, Arrays.asList("monster3"), "Player2"));
        when(playerRepository.findAll()).thenReturn(players);

        List<PlayerModel> result = playerService.listPlayers();

        assertEquals(players, result);
    }

    @Test
    void getPlayerReturnsPlayerById() {
        when(playerRepository.findById("1")).thenReturn(Optional.of(player));

        Optional<PlayerModel> result = playerService.getPlayer("1");

        assertEquals(Optional.of(player), result);
    }

    @Test
    void getPlayerReturnsEmptyWhenNotFound() {
        when(playerRepository.findById("1")).thenReturn(Optional.empty());

        Optional<PlayerModel> result = playerService.getPlayer("1");

        assertEquals(Optional.empty(), result);
    }

    @Test
    void deletePlayerReturnsMessage() {
        String playerId = "1";
        PlayerModel player = new PlayerModel(10, 100, 50, new ArrayList<>(), "TestPlayer");

        // Simule que le joueur existe
        when(playerRepository.findById(playerId)).thenReturn(Optional.of(player));

        // Simule la suppression du joueur
        doNothing().when(playerRepository).deleteById(playerId);

        String result = playerService.deletePlayer(playerId);

        // Vérifie que le message retourné est "Player deleted successfully"
        assertEquals("Player deleted successfully", result);
    }

    @Test
    void deletePlayerReturnsMessageWhenPlayerDoesNotExist() {
        when(playerRepository.existsById("1")).thenReturn(false);

        String result = playerService.deletePlayer("1");

        assertEquals("No such player", result);
    }

    @Test
    void levelUpReturnsUpdatedPlayer() {
        when(playerRepository.findById("1")).thenReturn(Optional.of(player));
        when(playerRepository.save(any(PlayerModel.class))).thenReturn(player);

        PlayerModel result = playerService.levelUp("1");

        assertEquals(player, result);
    }

    @Test
    void gainExpReturnsUpdatedPlayer() {
        when(playerRepository.findById("1")).thenReturn(Optional.of(player));
        when(playerRepository.save(any(PlayerModel.class))).thenReturn(player);

        PlayerModel result = playerService.gainExp("1", 10);

        assertEquals(player, result);
    }

    @Test
    void listMonstersOfPlayerReturnsMonsters() {
        //TODO
    }

}
*/