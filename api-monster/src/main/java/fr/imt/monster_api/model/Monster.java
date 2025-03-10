package fr.imt.monster_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.*;

import java.util.List;

@Data
@ToString
@NoArgsConstructor
@Document(collection = "monster")
public class Monster {

    @Id
    private String id;

    @NotNull
    private String name;

    @NotNull
    private ElementType element;

    @NotNull
    @JsonProperty(defaultValue = "0")
    private int level;

    @NotNull
    @JsonIgnore
    @JsonProperty(defaultValue = "0")
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
