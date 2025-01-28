package fr.imt.gatcha.auth_api.repository;

import fr.imt.gatcha.auth_api.domain.AppUser;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AppUserRepository extends MongoRepository<AppUser, String> {
}
