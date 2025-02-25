package fr.imt.monster_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.*;

import java.util.List;

@Document
@Getter
@Setter
@ToString
public class Monster {

    @Id
    private String id;

    @Enumerated(EnumType.STRING)
    @NotNull
    private ElementType element;

    @NotNull
    private double hp;

    @NotNull
    private double atk;

    @NotNull
    private double def;

    @NotNull
    private int vit;

@NotNull
@Size(max = 3)
    private List<Skill> skills;
}

@Document
@Getter
@Setter
@ToString
class Skill {

    @Id
    private Long id;

    @NotNull
    private double damage;

    @NotNull
    private double cooldown;

    @NotNull
    private int lvlMax;

    private Ratio ratio;

    private Monster monster;
}

@Document
@Getter
@Setter
@ToString
class Ratio {

    @Id
    private Long id;

    @NotNull
    private Stat stat;

    @NotNull
    private int percent;
}

// Différents éléments disponibles
enum ElementType {
    FIRE, WATER, EARTH, WIND, LIGHT, DARK
}

// Enumération des stats
enum Stat {
    HP, ATK, DEF, VIT
}
