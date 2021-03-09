package com.example.elibrary.repository;

import com.example.elibrary.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findById(String id);

    Optional<User> findByEmail(String id);
}
