package fr.imt.player_api.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Document(collection = "players")
public class PlayerModel {

    @Id
    private String id;

    @Setter
    @Min(0)
    @Max(50)
    private int level;

    @Setter
    private int exp;

    @Setter
    private List<String> monsters;

    @Setter
    private String name;

    @Setter
    private int xpForNextLevel;

    public PlayerModel(int level, List<String> monsters, String name) {
        this.level = level;
        this.exp = 0;
        this.monsters = monsters != null ? new ArrayList<>(monsters) : new ArrayList<>();
        this.name = name;
        this.xpForNextLevel = calculateXpForNextLevel(level);
    }

    /**
     * Vérifie si la configuration du joueur est invalide.
     */
    public void checkConfiguration() {
        if (monsters.size() > getMaxMonstersForLevel()) {
            throw new RuntimeException("The size of the monsters array is bigger than allowed");
        }
    }

    /**
     * Retourne le nombre maximum de monstres autorisés en fonction du niveau.
     */
    public int getMaxMonstersForLevel() {
        return 10 + level;
    }

    /**
     * Réduit la liste des monstres si elle dépasse la taille maximale autorisée.
     */
    public void increaseMonstersMaxSize() {
        int maxSize = getMaxMonstersForLevel();
        if (monsters.size() > maxSize) {
            monsters = monsters.subList(0, maxSize);
        }
    }

    /**
     * Met à jour l'XP nécessaire pour le prochain niveau.
     */
    public void updateXpForNextLevel() {
        this.xpForNextLevel = calculateXpForNextLevel(level);
    }

    /**
     * Calcule l'XP requis pour atteindre le prochain niveau.
     */
    private int calculateXpForNextLevel(int level) {
        return (int) (50 * Math.pow(1.1, level));
    }

    @Override
    public String toString() {
        return "PlayerModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", level=" + level +
                ", exp=" + exp +
                ", xpForNextLevel=" + xpForNextLevel +
                ", monsters=" + monsters +
                '}';
    }
}
