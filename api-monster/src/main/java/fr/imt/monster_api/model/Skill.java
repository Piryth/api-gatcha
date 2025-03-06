package fr.imt.monster_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.DefaultValue;
import lombok.Data;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Skill {

    @NotNull
    @JsonIgnore
    @DefaultValue("0")
    private int level;

    @NotNull
    private int lvlMax;

    @NotNull
    private double damage;

    @NotNull
    private double cooldown;

    private Ratio ratio;
}
