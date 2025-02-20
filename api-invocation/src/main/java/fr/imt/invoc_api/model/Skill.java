package fr.imt.invoc_api.model;

import lombok.Getter;
import lombok.Value;

@Value
@Getter
public class Skill {

    float dmg;
    int cooldown;
    int lvlMax;
    Ratio ratio;

}