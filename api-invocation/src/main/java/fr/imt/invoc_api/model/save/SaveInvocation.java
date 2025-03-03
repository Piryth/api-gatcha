package fr.imt.invoc_api.model.save;

import fr.imt.invoc_api.model.Invocation;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Value
@Getter
@Builder
@AllArgsConstructor
@Document(collection = "invocationSaves")
public class SaveInvocation {

    @Id
    String id;

    @NotNull
    String playerId;

    @CreatedDate
    String createdDate;

    @NotNull
    Invocation invocation;

}
