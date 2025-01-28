package fr.imt.gatcha.auth_api.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
@Document("users")
public class AppUser {
    @Id
    private String id;
    @Indexed(unique = true)

    private String username;

    @Indexed(unique = true)
    private String email;

    private String password;

    private String role;

}
