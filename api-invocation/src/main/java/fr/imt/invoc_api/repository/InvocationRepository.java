package fr.imt.invoc_api.repository;

import fr.imt.invoc_api.model.Invocation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvocationRepository extends MongoRepository<Invocation, String> {}
