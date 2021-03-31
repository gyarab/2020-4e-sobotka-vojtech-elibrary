package com.example.elibrary_final.repository;

import com.example.elibrary_final.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends MongoRepository<Book, String> {
    Optional<Book> findById(String id);

    List<Book> findByTitleContaining(String title);

    List<Book> findByOrderByDateOfPublishAsc();

    List<Book> findByAuthorsContaining(String author);

    List<Book> findByGenresContaining(String genre);

    List<Book> findByBelongsToUser(String user);

    List<Book> findByBelongsToUserIsNot(String string);
}
