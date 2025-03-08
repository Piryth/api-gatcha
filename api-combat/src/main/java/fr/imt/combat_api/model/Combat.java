package fr.imt.combat_api.model;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.*;

import java.util.List;

@Document
@Getter
@Setter
@ToString
public class Combat {

    @Id
    private String id;

    @NotNull
    private List<String> monsterIds;

    @NotNull
    private List<Round> rounds;

    @NotNull
    private String winner;
}
