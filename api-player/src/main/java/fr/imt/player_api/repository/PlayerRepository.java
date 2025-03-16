package fr.imt.player_api.repository;

import fr.imt.player_api.model.PlayerModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends MongoRepository<PlayerModel, String> {}
