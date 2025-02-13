package fr.imt.invoc_api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Builder
@AllArgsConstructor
@Document(collection = "invocation")
public class Invocation {

    @Id
    private String id;
    private Type element;
    private float hp;
    private float atk;
    private float def;
    private float vit;
    private Skill[] skills;
    private float lootRate;

}

