package fr.imt.player_api.model;


import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "players")
public class PlayerModel {

    @Id
    private String id;
    @Size(max = 50)
    private int level;
    private int exp;
    private int curr_exp;
    private List<String> monsters;

    public PlayerModel(int level, int exp, int curr_exp, List<String> monsters) {
        this.level = level;
        this.exp = exp;
        this.curr_exp = curr_exp;
        this.monsters = monsters;
    }

    public String getId(){
        return id;
    }
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getCurr_exp() {
        return curr_exp;
    }

    public void setCurr_exp(int curr_exp) {
        this.curr_exp = curr_exp;
    }

    public List<String> getMonsters() {
        return monsters;
    }

    public void setMonsters(List<String> monsters) {
        this.monsters = monsters;
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
