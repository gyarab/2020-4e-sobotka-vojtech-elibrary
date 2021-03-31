package com.example.elibrary_final.repository;

import com.example.elibrary_final.model.Genre;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface GenreRepository extends MongoRepository<Genre, String> {
    Optional<Genre> findById(String id);

    List<Genre>  findByNameContaining(String name);

    Optional<Genre> findByName(String name);

    Boolean existsByName(String name);


}
