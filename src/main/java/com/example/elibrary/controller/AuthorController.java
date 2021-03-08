package com.example.elibrary.controller;

import com.example.elibrary.model.Author;
import com.example.elibrary.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("/api")
public class AuthorController {
    @Autowired
    public AuthorService authorService;

    @PostMapping("/authors")
    public ResponseEntity<Author> addAuthor(@RequestBody Author author) {
        return new ResponseEntity<>(authorService.addAuthor(author), HttpStatus.OK);
    }

    @GetMapping("/authors")
    public ResponseEntity<List<Author>> getAuthors() {
        List<Author> authors = authorService.getAuthor();
        if (authors.isEmpty() == false) {
            return new ResponseEntity<>(authors, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

    }

    @GetMapping("/authors/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable("id") String id) {
        Author author = authorService.getAuthorByID(id);
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    @GetMapping("/authorsByName/{string}")
    public ResponseEntity<List<Author>> getAuthorByName(@PathVariable("string") String string) {
        List<Author> authorList = authorService.findAuthorByName(string);
        if (authorList.isEmpty() == false) {
            return new ResponseEntity<List<Author>>(authorList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/authors/{id}")
    public ResponseEntity<Author> updateBook(@RequestBody Author author, @PathVariable("id") String id) {
        return new ResponseEntity<>(authorService.updateAuthor(author, id), HttpStatus.OK);
    }


    @DeleteMapping("/authors/{id}")
    public void deleteAuthor(@PathVariable("id") String id) {
        authorService.deleteAuthor(id);
    }


    @DeleteMapping("/authors")
    public void deleteAllAuthors() {
        authorService.deleteAllAuthor();
    }
}

