package fr.imt.monster_api.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
@ToString
public class Ratio {

    @NotNull
    private Stat stat;

    @NotNull
    private int percent;
}