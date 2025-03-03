package fr.imt.invoc_api.model.response;

import fr.imt.invoc_api.model.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Value
@Getter
@Builder
@AllArgsConstructor
public class MonsterResponse {

    String id;
    Type element;
    double hp;
    double atk;
    double def;
    int vit;
    SkillResponse skills;

}
