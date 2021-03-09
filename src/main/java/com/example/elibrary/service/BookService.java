package com.example.elibrary.service;

import com.example.elibrary.model.Book;
import com.example.elibrary.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    GenreService genreService;
    @Autowired
    AuthorService authorService;

    public Book addBook(Book book) {
        Book _book = new Book(book.getTitle(), book.getDescription(), book.getYearOfPublish(), book.getGenresIds(), book.getAuthorIds());
        _book.setGenres(genreService.findGenresFromListOfIds(_book.getGenresIds()));
        _book.setAuthors(authorService.findAuthorsFromListOfIds(_book.getAuthorIds()));

        return bookRepository.save(_book);
    }

    public Book getBookByID(String id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        Book book = optionalBook.get();
        return book;
    }

    public Book updateBook(Book book, String id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        Book _book = optionalBook.get();
        if (book.getTitle() != null) {
            _book.setTitle(book.getTitle());
        }
        if (book.getDescription() != null) {
            _book.setTitle(book.getDescription());
        }
        if (book.getYearOfPublish() != 0) {
            _book.setYearOfPublish(book.getYearOfPublish());
        }
        if (book.getAuthorIds() != null) {
            _book.setAuthorIds(book.getAuthorIds());
            _book.setAuthors(authorService.findAuthorsFromListOfIds(_book.getAuthorIds()));
        }
        if (book.getGenresIds() != null) {
            _book.setGenresIds(book.getGenresIds());
            _book.setGenres(genreService.findGenresFromListOfIds(_book.getGenresIds()));
        }
        return bookRepository.save(_book);
    }

    public List<Book> findBooksByAuthorId(String id) {
        List<Book> books = bookRepository.findByAuthorIds(id);
        return books;
    }

    public List<Book> findBooksByGenreId(String id) {
        List<Book> books = bookRepository.findByGenresIds(id);
        return books;
    }

    public List<Book> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books;
    }

    public void deleteBookById(String id) {

        bookRepository.deleteById(id);
    }

    public void deleteAllBooks() {
        bookRepository.deleteAll();
    }

    public List<Book> findBookByTitle(String string) {
        List<Book> books = bookRepository.findBookByTitleContaining(string);
        return books;
    }
}


