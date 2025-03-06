package fr.imt.combat_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import jakarta.ws.rs.DefaultValue;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.*;

import java.util.List;

@Document
@Getter
@Setter
@ToString
public class Round {

    @Id
    private String id;

    @NotNull
    private String idAttacker;

    @NotNull
    private String idSkillUsed;
}
