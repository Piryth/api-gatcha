package fr.imt.invoc_api.dto;

import fr.imt.invoc_api.model.ElementType;
import fr.imt.invoc_api.model.Skill;
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
    String name;
    ElementType element;
    int level;
    int upgradePoints;
    double hp;
    double atk;
    double def;
    int vit;
    List<Skill> skills;

}
