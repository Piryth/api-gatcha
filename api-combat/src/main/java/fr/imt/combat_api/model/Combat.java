package fr.imt.combat_api.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
