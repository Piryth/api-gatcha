package fr.imt.invoc_api.dto;

import fr.imt.invoc_api.model.ElementType;
import fr.imt.invoc_api.model.Skill;
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
    private List<Skill> skills;

}
