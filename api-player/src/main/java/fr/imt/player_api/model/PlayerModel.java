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
    @Setter
    private String name;

    public PlayerModel(int level, int exp, int curr_exp, List<String> monsters, String name) {
        this.level = level;
        this.exp = exp;
        this.curr_exp = curr_exp;
        this.monsters = monsters;
        this.name = name;
    }

    public boolean invalidPlayerConfiguration() {
        return this.monsters.size() > 10 + this.getLevel() || this.exp != getTotalXP(this.getLevel());
    }

    public double getTotalXP(int level) {
        return 50 + 50 * (1 - Math.pow(1.1, level - 1)) / (1 - 1.1);
    }



    public void increaseMonstersMaxSize() {
        int newMaxMonsters = 10 + getLevel();
        if (monsters.size() > newMaxMonsters) {
            monsters = monsters.subList(0, newMaxMonsters);
        }
    }

    public void increaseNecessaryExpForLevelUp() {
        int xpForNextLevel = (int) (50 * Math.pow(1.1, getLevel() - 1));
        setExp(xpForNextLevel);
    }



    @Override
    public String toString() {
        return "PlayerModel{" +
                "id='" + id + '\'' +
                "name='" + name + '\'' +
                ", level=" + level +
                ", exp=" + exp +
                ", curr_exp=" + curr_exp +
                ", monsters=" + monsters +
                '}';
    }
}
