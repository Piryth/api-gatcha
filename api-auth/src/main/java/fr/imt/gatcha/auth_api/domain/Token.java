package fr.imt.gatcha.auth_api.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("tokens")
@Builder
@Getter
public class Token {

    @Id
    private String id;
    @Indexed(unique = true)
    private String token;
    private LocalDateTime expires;
}
