package fr.imt.player_api.model;


import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Document(collection = "players")
public class PlayerModel {

    @Id
    private String id;
    @Setter
    @Size(max = 50)
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
