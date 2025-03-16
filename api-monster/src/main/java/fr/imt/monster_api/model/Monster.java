package fr.imt.monster_api.model;

import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.*;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "monster")
public class Monster {

    @Id
    private String id;
    private String name;
    private ElementType element;
    private int level = 0;
    private int upgradePoints = 0;
    private double hp;
    private double atk;
    private double def;
    private int vit;
    @Size(max = 3)
    private List<Skill> skills;

}
