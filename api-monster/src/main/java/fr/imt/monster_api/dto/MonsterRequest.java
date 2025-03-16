package fr.imt.monster_api.dto;

import fr.imt.monster_api.model.ElementType;
import fr.imt.monster_api.model.Monster;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MonsterRequest {

    private String name;
    private ElementType element;
    private double hp;
    private double atk;
    private double def;
    private int vit;
    private List<SkillRequest> skills;

    public Monster convertToMonster() {
        return Monster.builder()
                .name(this.getName())
                .element(this.getElement())
                .hp(this.getHp())
                .atk(this.getAtk())
                .def(this.getDef())
                .vit(this.getVit())
                .skills(skills.stream().map(SkillRequest::convertToSkill).toList())
                .build();
    }
}
