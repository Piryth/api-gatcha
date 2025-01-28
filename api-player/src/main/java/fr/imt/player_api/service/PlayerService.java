package fr.imt.player_api.service;

import fr.imt.player_api.model.PlayerModel;
import fr.imt.player_api.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    public PlayerModel createPlayer(PlayerModel player) {
        try {
            return playerRepository.save(player);
        } catch (Exception e) {
            throw new RuntimeException("Error creating player", e);
        }
    }

    public List<PlayerModel> listPlayers() {
        try {
            return playerRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error getting players", e);
        }
    }

    public Optional<PlayerModel> getPlayer(String id) {
        try {
            return playerRepository.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error getting player", e);
        }
    }

    public String deletePlayer(String id) {
        try {
            Optional<PlayerModel> playerToDelete = playerRepository.findById(id);
            if (playerToDelete.isEmpty()) {
                return "No such player";
            } else {
                playerRepository.deleteById(id);
                return "Player deleted successfully";
            }
        } catch (Exception e) {
            throw new RuntimeException("Error deleting player", e);
        }
    }

    public String updatePlayer(String id, PlayerModel updatedPlayer) {
        try {
            Optional<PlayerModel> playerToUpdate = playerRepository.findById(id);
            if (playerToUpdate.isEmpty()) {
                return "No such player";
            } else {
                PlayerModel existingPlayer = playerToUpdate.get();
                existingPlayer.setExp(updatedPlayer.getExp());
                existingPlayer.setLevel(updatedPlayer.getLevel());
                existingPlayer.setCurr_exp(updatedPlayer.getCurr_exp());
                existingPlayer.setMonsters(updatedPlayer.getMonsters());
                return "Player updated successfuly";
            }
        } catch (Exception e) {
            throw new RuntimeException("Error updating player", e);
        }
    }


}
