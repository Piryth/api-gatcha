package fr.imt.monster_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import jakarta.ws.rs.DefaultValue;
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

    @NotNull
    private String name;

    @Enumerated(EnumType.STRING)
    @NotNull
    private ElementType element;

    @NotNull
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
