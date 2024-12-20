package com.alice.examensarbete_backend.repository;

import com.alice.examensarbete_backend.database.AuthorDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends MongoRepository <AuthorDocument,String> {
  Optional<AuthorDocument> findByKey(String key);


}
