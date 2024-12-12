package com.alice.examensarbete_backend.repository;

import com.alice.examensarbete_backend.database.BookDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends MongoRepository<BookDocument, String> {
}
