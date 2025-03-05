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
                    .orElseThrow(() -> new RuntimeException("No such player"));

            // Vérifier si le joueur est au niveau max
            if (player.getLevel() >= 50) {
                throw new RuntimeException("Player has reached the maximum level");
            }

            // Augmenter le niveau
            int newLevel = player.getLevel() + 1;
            player.setLevel(newLevel);

            // Calculer l'XP excédentaire (XP actuelle - XP nécessaire pour le niveau suivant)
            int excessXP = player.getExp() - player.getXpForNextLevel();

            // Réinitialiser l'XP à 0 après le level-up
            player.setExp(0);

            // Si l'excédent est positif, on le garde comme XP pour le prochain niveau
            if (excessXP > 0) {
                player.setExp(excessXP);  // L'XP excédentaire est reportée pour le niveau suivant
            }

            // Augmenter la taille maximale de la liste des monstres en fonction du niveau
            player.increaseMonstersMaxSize();

            // Mettre à jour l'XP nécessaire pour le prochain level-up
            player.updateXpForNextLevel();

            // Sauvegarder les modifications du joueur dans le repository
            playerRepository.save(player);

            return player;
        } catch (Exception e) {
            throw new RuntimeException("Error leveling up the player", e);
        }
    }


    public int getLevelOfPlayer(String userId) {
        return playerRepository.findById(userId)
                .map(PlayerModel::getLevel)
                .orElseThrow(() -> new RuntimeException("Player not found"));
    }

    public PlayerModel createPlayer(PlayerModel player) {
        player.checkConfiguration();
        return playerRepository.save(player);
    }

    public List<PlayerModel> listPlayers() {
        return playerRepository.findAll();
    }

    public PlayerModel getPlayer(String id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Player not found"));
    }

    public String deletePlayer(String id) {
        if (!playerRepository.existsById(id)) {
            return "No such player";
        }
        playerRepository.deleteById(id);
        return "Player deleted successfully";
    }

    public PlayerModel gainExp(String userId, int exp) {
        PlayerModel player = playerRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("No such player"));

        // Ajouter l'expérience
        player.setExp(player.getExp() + exp);

        // Vérifier si le joueur doit level-up
        while (player.getExp() >= player.getXpForNextLevel()) {
            levelUp(userId);  // Après chaque level-up, l'XP excédentaire est conservée
            player = playerRepository.findById(userId) // Récupérer à nouveau le joueur avec l'XP mis à jour
                    .orElseThrow(() -> new RuntimeException("No such player"));
        }

        // Enregistrer le joueur mis à jour
        playerRepository.save(player);
        return player;
    }



    public List<String> listMonstersOfPlayer(String userId) {
        PlayerModel player = playerRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("No such player"));
        return player.getMonsters();
    }

    public String addMonsterToPlayer(String userId, String monsterId) {
        PlayerModel player = playerRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("No such player"));

        // Vérifier si on peut encore ajouter un monstre
        if (player.getMonsters().size() >= player.getMaxMonstersForLevel()) {
            throw new RuntimeException("Player already has maximum number of monsters");
        }

        player.getMonsters().add(monsterId);
        playerRepository.save(player);

        return "Monster added successfully";
    }
}
