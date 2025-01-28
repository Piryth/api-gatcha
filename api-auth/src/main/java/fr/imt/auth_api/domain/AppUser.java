package fr.imt.auth_api.domain;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@AllArgsConstructor
@Getter
@Document("users")
public class AppUser {

    @Id
    private String id;

    @Indexed(unique = true)
    @NotNull
    private String username;

    @Indexed(unique = true)
    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String role;

}
