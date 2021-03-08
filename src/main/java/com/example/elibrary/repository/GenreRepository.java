package com.example.elibrary.repository;

import com.example.elibrary.model.Author;
import com.example.elibrary.model.Genre;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface GenreRepository extends MongoRepository<Genre, String> {
    List<Genre> findByGenreNameContaining(String name);

    List<Author> findByGenreName(String name);
}
