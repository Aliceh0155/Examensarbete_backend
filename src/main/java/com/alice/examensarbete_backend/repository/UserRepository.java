package com.alice.examensarbete_backend.repository;

import com.alice.examensarbete_backend.database.CustomUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface UserRepository extends MongoRepository<CustomUser, String> {

  boolean existsByUsername(String username);

  Optional<CustomUser> findByUsername(String username);
}