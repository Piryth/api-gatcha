package fr.imt.invoc_api.model.response;

import fr.imt.invoc_api.model.ElementType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import java.util.List;

@Value
@Getter
@Builder
@AllArgsConstructor
public class MonsterResponse {

    String id;
    ElementType element;
    double hp;
    double atk;
    double def;
    int vit;
    List<SkillResponse> skills;

}
