package fr.imt.invoc_api.dto;

import fr.imt.invoc_api.model.Ratio;
import lombok.Value;

@Value
public class SkillResponse {

    int level;
    int lvlMax;
    float dmg;
    int cooldown;
    Ratio ratio;

}
