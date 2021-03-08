package com.example.elibrary.repository;

import com.example.elibrary.model.Author;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AuthorRepository extends MongoRepository<Author, String> {
    List<Author> findByNameContaining(String name);

    List<Author> findByName(String name);

}
