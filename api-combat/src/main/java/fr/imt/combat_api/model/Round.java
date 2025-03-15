package fr.imt.combat_api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Document
@Data
public class Round {
    @Id
    private String id;
    private String idAttacker;
    private String idSkillUsed;
    private double damageDealt;
}
