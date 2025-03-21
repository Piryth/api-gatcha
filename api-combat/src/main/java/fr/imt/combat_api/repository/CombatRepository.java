package fr.imt.combat_api.repository;

import fr.imt.combat_api.model.Combat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CombatRepository extends MongoRepository<Combat, String> {
}
