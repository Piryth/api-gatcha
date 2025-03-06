package fr.imt.auth_api.repository;

import fr.imt.auth_api.domain.AppUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AppUserRepository extends MongoRepository<AppUser, String> {

    Optional<AppUser> findAppUserByUsername(String username);

}
