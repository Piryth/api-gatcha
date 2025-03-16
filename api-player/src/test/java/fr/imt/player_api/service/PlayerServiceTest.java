package fr.imt.player_api.service;

import fr.imt.player_api.model.PlayerModel;
import fr.imt.player_api.repository.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerService playerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void levelUpIncreasesPlayerLevel() {
        List<String> monsters = Arrays.asList("monster1", "monster2");
        PlayerModel player = new PlayerModel(1, null, "Player1");
        player.setXpForNextLevel(100);

        when(playerRepository.findById(player.getId())).thenReturn(Optional.of(player));
        when(playerRepository.save(player)).thenReturn(player);

        PlayerModel updatedPlayer = playerService.levelUp(player.getId());

        assertEquals(2, updatedPlayer.getLevel());
        assertEquals(0, updatedPlayer.getExp());
        verify(playerRepository, times(1)).save(player);
    }

    @Test
    void getLevelOfPlayerReturnsCorrectLevel() {
        List<String> monsters = Arrays.asList("monster1", "monster2");
        PlayerModel player = new PlayerModel(1, null, "Player1");

        when(playerRepository.findById(player.getId())).thenReturn(Optional.of(player));

        int level = playerService.getLevelOfPlayer(player.getId());

        assertEquals(1, level);
    }

    @Test
    void createPlayerSavesPlayer() {
        List<String> monsters = Arrays.asList("monster1", "monster2");
        PlayerModel player = new PlayerModel(1, null, "Player1");

        when(playerRepository.save(player)).thenReturn(player);

        PlayerModel createdPlayer = playerService.createPlayer(player);

        assertEquals(player, createdPlayer);
        verify(playerRepository, times(1)).save(player);
    }

    @Test
    void listPlayersReturnsAllPlayers() {
        List<String> monsters = Arrays.asList("monster1", "monster2");
        List<PlayerModel> players = Arrays.asList(
                new PlayerModel(1, null, "Player1"),
                new PlayerModel(2, null, "Player2")
        );

        when(playerRepository.findAll()).thenReturn(players);

        List<PlayerModel> result = playerService.listPlayers();

        assertEquals(players, result);
    }

    @Test
    void getPlayerReturnsPlayerById() {
        List<String> monsters = Arrays.asList("monster1", "monster2");
        PlayerModel player = new PlayerModel(1, null, "Player1");

        when(playerRepository.findById(player.getId())).thenReturn(Optional.of(player));

        PlayerModel result = playerService.getPlayer(player.getId());

        assertEquals(player, result);
    }

    @Test
    void deletePlayerReturnsSuccessMessage() {
        String id = "1";
        when(playerRepository.existsById(id)).thenReturn(true);

        String result = playerService.deletePlayer(id);

        assertEquals("Player deleted successfully", result);
        verify(playerRepository, times(1)).deleteById(id);
    }

    @Test
    void gainExpIncreasesPlayerExp() {
        List<String> monsters = Arrays.asList("monster1", "monster2");
        PlayerModel player = new PlayerModel(1, null, "Player1");
        player.setXpForNextLevel(100);

        when(playerRepository.findById(player.getId())).thenReturn(Optional.of(player));
        when(playerRepository.save(player)).thenReturn(player);

        PlayerModel updatedPlayer = playerService.gainExp(player.getId(), 30);

        assertEquals(30, updatedPlayer.getExp());
        verify(playerRepository, times(1)).save(player);
    }

    @Test
    void listMonstersOfPlayerReturnsMonsters() {
        List<String> monsters = Arrays.asList("monster1", "monster2");
        PlayerModel player = new PlayerModel(1, monsters, "Player1");

        when(playerRepository.findById(player.getId())).thenReturn(Optional.of(player));

        List<String> result = playerService.listMonstersOfPlayer(player.getId());

        assertEquals(monsters, result);
    }

    @Test
    void addMonsterToPlayerAddsMonster() {
        List<String> monsters = Arrays.asList("monster1", "monster2");
        PlayerModel player = new PlayerModel(1, monsters, "Player1");

        when(playerRepository.findById(player.getId())).thenReturn(Optional.of(player));
        when(playerRepository.save(player)).thenReturn(player);

        String result = playerService.addMonsterToPlayer(player.getId(), "monster3");

        assertEquals("Monster added successfully", result);
        assertTrue(player.getMonsters().contains("monster3"));
        verify(playerRepository, times(1)).save(player);
    }

    @Test
    void addMonsterToPlayerThrowsExceptionWhenMaxMonstersReached() {
        List<String> monsters = Arrays.asList("monster1", "monster2", "monster3", "monster4",
                "monster5", "monster6", "monster7", "monster8",
                "monster9", "monster10", "monster11");
        PlayerModel player = new PlayerModel(1, monsters, "Player1");

        when(playerRepository.findById(player.getId())).thenReturn(Optional.of(player));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> playerService.addMonsterToPlayer(player.getId(), "monster12"));
        assertEquals("Player already has maximum number of monsters", exception.getMessage());
    }
}
