package fr.imt.monster_api.dto;

import fr.imt.monster_api.model.Ratio;
import fr.imt.monster_api.model.Skill;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Value
@Getter
@Builder
public class SkillRequest {

    float dmg;
    int cooldown;
    int lvlMax;
    Ratio ratio;

    public Skill convertToSkill() {
        return Skill.builder()
                .dmg(dmg)
                .cooldown(cooldown)
                .lvlMax(lvlMax)
                .ratio(ratio)
                .build();
    }

}
