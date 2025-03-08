package fr.imt.combat_api.model;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.*;

@Document
@Getter
@Setter
@ToString
public class Round {

    @Id
    private String id;

    private String idAttacker;
    private String idSkillUsed;
    private double damageDealt;

}
