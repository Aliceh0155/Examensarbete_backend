package com.alice.examensarbete_backend.repository;

import com.alice.examensarbete_backend.entity.AuthorDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends MongoRepository <AuthorDocument,String> {


}
