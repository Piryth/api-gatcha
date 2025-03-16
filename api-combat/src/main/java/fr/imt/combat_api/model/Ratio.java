package fr.imt.combat_api.model;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.mongodb.core.mapping.Document;

@Value
@Builder
@Document
public class Ratio {
    Stat stat;
    float percent;
}
