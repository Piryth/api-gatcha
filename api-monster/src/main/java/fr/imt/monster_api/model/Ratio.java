package fr.imt.monster_api.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.Value;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Value
@ToString
public class Ratio {

    @NotNull
    Stat stat;

    @NotNull
    int percent;
}