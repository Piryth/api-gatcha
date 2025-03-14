package fr.imt.invoc_api.model;

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
    float hp;
    float atk;
    float def;
    float vit;
    List<Skill> skills;
    float lootRate;

}
