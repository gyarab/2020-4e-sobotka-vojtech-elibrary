package com.example.elibrary.repository;

import com.example.elibrary.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends MongoRepository<Book, String> {
    List<Book> findByTitleEquals(String name);

    Optional<Book> findById(String id);

    List<Book> findByAuthorIds(String id);

    List<Book> findBookByTitleContaining(String id);

    List<Book> findByGenresIds(String id);
}
