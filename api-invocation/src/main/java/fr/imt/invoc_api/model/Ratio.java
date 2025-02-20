package fr.imt.invoc_api.model;

import lombok.Getter;
import lombok.Value;

@Value
@Getter
public class Ratio {

    Stat stat;
    float percent;

}
