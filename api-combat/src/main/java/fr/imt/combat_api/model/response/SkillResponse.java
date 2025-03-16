package fr.imt.combat_api.model.response;

import fr.imt.combat_api.model.Ratio;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class SkillResponse {

    @Id
    private String id;

    @NotNull
    private int level;

    @NotNull
    private int lvlMax;

    @NotNull
    double damage;

    @NotNull
    int cooldown;

    @NotNull
    Ratio ratio;

}
