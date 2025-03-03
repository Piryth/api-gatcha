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

    public PlayerModel levelUp(String userId) {
        try {
            // Chercher le joueur par ID
            PlayerModel player = playerRepository.findById(userId)
                    .orElseThrow(() -> new Exception("No such player"));

            // Augmenter le niveau
            int newLevel = player.getLevel() + 1;
            player.setLevel(newLevel);

            // Réinitialiser l'XP à 0 après le level-up
            player.setCurr_exp(0);

            // Augmenter la taille maximale de la liste des monstres en fonction du niveau
            player.increaseMonstersMaxSize();

            // Mettre à jour l'XP nécessaire pour le prochain level-up
            player.increaseNecessaryExpForLevelUp();

            // Sauvegarder les modifications du joueur dans le repository
            playerRepository.save(player);

            return player;

        } catch (Exception e) {
            throw new RuntimeException("Error leveling up the player", e);
        }
    }

    public int getLevelOfPlayer(String userId) {
        try {
            Optional<PlayerModel> player = playerRepository.findById(userId);
           return player.get().getLevel();
        } catch (Exception e) {
            throw new RuntimeException("Error getting level of a player", e);
        }
    }

    public PlayerModel createPlayer(PlayerModel player) {
        try {
            if (player.invalidPlayerConfiguration()){
                throw new Exception("Player configuration is invalid");
            }
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

    public PlayerModel gainExp(String userId, int exp) {
        Optional<PlayerModel> player = playerRepository.findById(userId);
        player.get().setCurr_exp(player.get().getCurr_exp() + exp);
        playerRepository.save(player.get());
        return player.get();
    }


    public List<Object> listMonstersOfPlayer(String userId){
        try {
            Optional<PlayerModel> player =  playerRepository.findById(userId);
            //appel à l'api de félix avec player.monsters
            //Return la liste
            return List.of();
        } catch (Exception e) {
            throw new RuntimeException("Error getting monsters of a player", e);
        }
    }


}
