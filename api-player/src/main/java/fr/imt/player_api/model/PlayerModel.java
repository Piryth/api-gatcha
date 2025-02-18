package fr.imt.player_api.model;


import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Document(collection = "players")
public class PlayerModel {

    @Id
    private String id;
    @Setter
    @Size(max = 50, min = 0)
    private int level;
    @Setter
    private int exp;
    @Setter
    private int curr_exp;
    @Setter
    private List<String> monsters;

    public PlayerModel(int level, int exp, int curr_exp, List<String> monsters) {
        this.level = level;
        this.exp = exp;
        this.curr_exp = curr_exp;
        this.monsters = monsters;
    }

    public boolean invalidMonsterConfiguration() {
        return this.monsters.size() < 10 + this.getLevel();
    }

    public void increaseMonstersMaxSize() {
        int newMaxMonsters = 10 + getLevel();
        if (monsters.size() > newMaxMonsters) {
            monsters = monsters.subList(0, newMaxMonsters);
        }
    }

    public void increaseNecessaryExpForLevelUp() {
        // Calcule l'XP nécessaire pour le niveau suivant, basé sur la formule donnée
        int xpForNextLevel = (int) (50 * Math.pow(1.1, getLevel() - 1));
        // Mettre à jour l'XP nécessaire dans le modèle (si ce champ existe)
        setExp(xpForNextLevel);
    }



    @Override
    public String toString() {
        return "PlayerModel{" +
                "id='" + id + '\'' +
                ", level=" + level +
                ", exp=" + exp +
                ", curr_exp=" + curr_exp +
                ", monsters=" + monsters +
                '}';
    }
}
