package fr.imt.gatcha.auth_api.repository;

import fr.imt.gatcha.auth_api.domain.Token;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TokenRepository extends MongoRepository<Token, String> {

    Optional<Token> findByToken(String token);
}
