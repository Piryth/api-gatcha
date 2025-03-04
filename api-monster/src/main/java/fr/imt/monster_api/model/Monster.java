package fr.imt.monster_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import jakarta.ws.rs.DefaultValue;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.*;

import java.util.List;

@Getter
@Setter
@ToString
@Document(collection = "monster")
public class Monster {

    @Id
    private String id;

    @Enumerated(EnumType.STRING)
    @NotNull
    private ElementType element;

    @NotNull
    @JsonIgnore
    @DefaultValue("0")
    private int level;

    @NotNull
    @JsonIgnore
    @DefaultValue("0")
    private int upgradePoints;

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
