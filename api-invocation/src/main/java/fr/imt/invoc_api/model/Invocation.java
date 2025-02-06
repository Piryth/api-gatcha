package fr.imt.invoc_api.model;

import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.UUID;

@Getter
@Document(collection = "invocation")
public class Invocation {

    @MongoId
    private UUID id;
    private Type element;
    private float hp;
    private float atk;
    private float def;
    private float vit;
    private Skill[] skills;
    private float lootRate;

}

