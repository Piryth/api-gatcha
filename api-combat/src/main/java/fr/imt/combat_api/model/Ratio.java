package fr.imt.combat_api.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Value
@Document
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class Ratio {

    Stat stat;
    float percent;

}
