package com.example.elibrary.controller;


import com.example.elibrary.model.Book;
import com.example.elibrary.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("/api")
public class BookController {
    @Autowired
    BookService bookService;

    @PostMapping("/books")
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        return new ResponseEntity<>(bookService.addBook(book), HttpStatus.OK);

    }

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAllBooks() {
        return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable String id) {
        Book book = bookService.getBookByID(id);
        if (!book.equals(null)) {
            return new ResponseEntity<>(book, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<Book> updateBookById(@RequestBody Book book, @PathVariable String id) {
        return new ResponseEntity<Book>(bookService.updateBook(book, id), HttpStatus.OK);
    }

    @DeleteMapping("/books/{id}")
    public void deleteBookByID(@PathVariable String id) {
        bookService.deleteBookById(id);
    }

    @GetMapping("books/byAuthor/{id}")
    public ResponseEntity<List<Book>> findBooksByAuthor(@PathVariable String id) {
        List<Book> books = bookService.findBooksByAuthorId(id);
        if (!books.isEmpty()) {
            return new ResponseEntity<>(books, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("books/byGenre/{id}")
    public ResponseEntity<List<Book>> findBooksByGenre(@PathVariable String id) {
        List<Book> books = bookService.findBooksByGenreId(id);
        if (!books.isEmpty()) {
            return new ResponseEntity<>(books, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("books/byTitle/{string}")
    public ResponseEntity<List<Book>> findBooksByTitle(@PathVariable String string) {
        List<Book> books = bookService.findBookByTitle(string);
        if (!books.isEmpty()) {
            return new ResponseEntity<>(books, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/books")
    public void deleteAllBooks() {
        bookService.deleteAllBooks();
    }
}
