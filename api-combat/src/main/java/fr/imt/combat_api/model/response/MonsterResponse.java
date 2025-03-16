package fr.imt.combat_api.model.response;

import fr.imt.combat_api.model.Type;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import org.springframework.data.annotation.Id;

import java.util.List;

@Value
@Getter
@Builder
@AllArgsConstructor
public class MonsterResponse {

    @Id
    String id;

    @NotNull
    Type element;

    @NotNull
    double hp;

    @NotNull
    double atk;

    @NotNull
    double def;

    @NotNull
    int vit;

    @NotNull
    @Size(max = 3)
    List<SkillResponse> skills;

}
