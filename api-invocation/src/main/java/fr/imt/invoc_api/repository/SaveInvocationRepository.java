package fr.imt.invoc_api.repository;

import fr.imt.invoc_api.model.save.SaveInvocation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaveInvocationRepository extends MongoRepository<SaveInvocation, String> {
}
