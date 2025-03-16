package fr.imt.invoc_api.model;

import fr.imt.invoc_api.dto.MonsterRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Value
@Builder
@AllArgsConstructor
@Document(collection = "invocation")
public class Invocation {

    @Id
    String id;
    String name;
    ElementType element;
    double hp;
    double atk;
    double def;
    int vit;
    List<Skill> skills;
    float lootRate;

    public MonsterRequest convertToMonsterRequest() {
        return MonsterRequest.builder()
                .name(this.getName())
                .element(this.getElement())
                .hp(this.getHp())
                .atk(this.getAtk())
                .def(this.getDef())
                .vit(this.getVit())
                .skills(this.getSkills())
                .build();
    }

}
