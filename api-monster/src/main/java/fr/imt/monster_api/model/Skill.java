package fr.imt.monster_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.DefaultValue;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document
public class Skill {

    @NotNull
    @DefaultValue("0")
    private int level;

    @NotNull
    private int lvlMax;

    @NotNull
    private double dmg;

    @NotNull
    private double cooldown;

    private Ratio ratio;
}
