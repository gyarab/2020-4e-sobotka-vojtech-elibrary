package com.example.elibrary_final.repository;

import com.example.elibrary_final.model.User;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findById(String id);

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    List<User> findByUsernameContaining(String username);
    DeleteQuery deleteByUsername(String username);

}
