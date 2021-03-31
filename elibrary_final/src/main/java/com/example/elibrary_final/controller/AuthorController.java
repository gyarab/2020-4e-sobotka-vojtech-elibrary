package com.example.elibrary_final.controller;

import com.example.elibrary_final.model.Author;
import com.example.elibrary_final.repository.AuthorRepository;
import com.example.elibrary_final.service.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class AuthorController {

    @Autowired
    AuthorRepository authorRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/authors")
    public ResponseEntity<? extends Object> createAuthor(@RequestBody Author author) {
        if (authorRepository.existsByName(author.getName())) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        if (!author.getName().isEmpty()) {
            return new ResponseEntity<>(authorRepository.save(author), HttpStatus.OK);
        } else {
            return new ResponseEntity<MessageResponse>(new MessageResponse("missing Author title"), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/authors")
    public ResponseEntity<? extends Object> findAuthorsById(@RequestParam(required = false) String id) {
        // if id is null Method return all Authors
        if (id == null) {
            List<Author> Authors = authorRepository.findAll();
            if (!Authors.isEmpty()) {
                return new ResponseEntity<List<Author>>(Authors, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

        } else {
            Optional<Author> optionalAuthor = authorRepository.findById(id);

            if (!optionalAuthor.isEmpty()) {
                Author author = optionalAuthor.get();
                return new ResponseEntity<>(author, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/authors")
    public ResponseEntity<Author> updateAuthor(@RequestParam String id) {
        Optional<Author> optionalAuthor = authorRepository.findById(id);
        if (optionalAuthor.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Author Author = optionalAuthor.get();
        Author updatedAuthor = new Author();
        if (Author.getName() != null) {
            updatedAuthor.setName(Author.getName());
        }

        return new ResponseEntity<>(authorRepository.save(updatedAuthor), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/authors")
    public ResponseEntity<HttpStatus> deleteAuthorById(@RequestParam String id) {
        if (authorRepository.existsById(id)) {
            authorRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/authors/deleteAll")
    public ResponseEntity<HttpStatus> deleteAllAuthors() {
        authorRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);

    }
}

