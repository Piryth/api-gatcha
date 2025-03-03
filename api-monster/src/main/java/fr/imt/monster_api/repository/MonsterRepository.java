package fr.imt.monster_api.repository;

import fr.imt.monster_api.model.Monster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonsterRepository extends MongoRepository<Monster, String> {

}
