package fr.imt.monster_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import jakarta.ws.rs.DefaultValue;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.*;

import java.util.List;

@Document
@Builder
@Data
@ToString
@AllArgsConstructor
public class Monster {

    @Id
    private String id;

    @Enumerated(EnumType.STRING)
    @NotNull
    private final ElementType element;

    @NotNull
    @JsonIgnore
    @DefaultValue("0")
    private int level;

    @NotNull
    @JsonIgnore
    @DefaultValue("0")
    private final int upgradePoints;

    @NotNull
    private final double hp;

    @NotNull
    private final double atk;

    @NotNull
    private final double def;

    @NotNull
    private final int vit;

    @NotNull
    @Size(max = 3)
    private List<Skill> skills;
}
