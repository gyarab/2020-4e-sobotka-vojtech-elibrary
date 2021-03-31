package com.example.elibrary_final.repository;

import com.example.elibrary_final.model.Author;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends MongoRepository<Author, String> {
    Optional<Author> findById(String id);

    List<Author> findByNameContaining(String name);

    Optional<Author> findByName(String name);

    Boolean existsByName(String name);

}
