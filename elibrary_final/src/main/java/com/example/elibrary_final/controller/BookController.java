package com.example.elibrary_final.controller;

import com.example.elibrary_final.model.Book;
import com.example.elibrary_final.repository.BookRepository;
import com.example.elibrary_final.service.FilterReq;
import com.example.elibrary_final.service.MessageResponse;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;


@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class BookController {
    @Autowired
    BookRepository bookRepository;

    @PostMapping("/books")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<? extends Object> createBook(@RequestBody Book book) {
        if (!book.getTitle().isEmpty()) {
            book.setReturnDate(null);
            book.setBelongsToUser("available");
            return new ResponseEntity<>(bookRepository.save(book), HttpStatus.OK);
        } else {
            return new ResponseEntity<MessageResponse>(new MessageResponse("missing book title"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/books/byDateOfPublishAsc")
    public ResponseEntity<List<Book>> getBooksByDateOfPublishAsc() {
        List<Book> books = bookRepository.findByOrderByDateOfPublishAsc();
        if (!books.isEmpty()) {
            return new ResponseEntity<>(books, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PreAuthorize("hasRole('ADMIN')or hasRole('USER')")
    @GetMapping("/books")
    public ResponseEntity<? extends Object> findAllBooks() {
        List<Book> bookList = bookRepository.findAll();
        if (!bookList.isEmpty()) {
            return new ResponseEntity<>(bookList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PreAuthorize("hasRole('ADMIN')or hasRole('USER')")
    @GetMapping("/books/{id}")
    public ResponseEntity<? extends Object> findBookById(@PathVariable String id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            return new ResponseEntity<>(book, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/books/byAuthor")
    public ResponseEntity<List<Book>> findBooksByAuthor(@RequestParam String author) {
        List<Book> bookList = bookRepository.findByAuthorsContaining(author);
        if (!bookList.isEmpty()) {
            return new ResponseEntity<>(bookList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/books/byGenre")
    public ResponseEntity<List<Book>> findBooksByGenre(@RequestParam String genre) {
        List<Book> bookList = bookRepository.findByGenresContaining(genre);
        if (!bookList.isEmpty()) {
            return new ResponseEntity<>(bookList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/books/find/{input}")
    public ResponseEntity<List<Book>> findBookByString(@PathVariable String input) {
        List<Book> bookList = bookRepository.findByTitleContaining(input);

        if (!bookList.isEmpty()) {
            return new ResponseEntity<>(bookList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/books/findByParameters")
    public ResponseEntity<List<Book>> filterBooks(@RequestBody FilterReq filterReq) {
        String[] authors = filterReq.getAuthors();
        String[] genres = filterReq.getGenres();
        HashSet<String> idlist = new HashSet<>();
        List<Book> resultList = new ArrayList<>();
        if (filterReq.getAuthors() != null) {
            for (int i = 0; i < authors.length; i++) {
                resultList.addAll(bookRepository.findByAuthorsContaining(authors[i]));
            }
        }
        if (filterReq.getGenres() != null) {
            for (int i = 0; i < genres.length; i++) {
                resultList.addAll(bookRepository.findByGenresContaining(genres[i]));
            }
        }
        if (resultList.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        for (int i = 0; i < resultList.size(); i++) {
            idlist.add(resultList.get(i).getId());
        }
        System.out.println(idlist);
        List<Book> finalList = new ArrayList<>();
        String[] idArray = idlist.toArray(new String[idlist.size()]);
        for (int i = 0; i < idlist.size(); i++) {
            finalList.add(bookRepository.findById(idArray[i]).get());
        }
        return new ResponseEntity<>(finalList, HttpStatus.OK);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/books/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable String id, @RequestBody Book book) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Book updatedBook = optionalBook.get();
        if (book.getTitle() != null) {
            updatedBook.setTitle(book.getTitle());
        }
        if (book.getDescription() != null) {
            updatedBook.setDescription(book.getDescription());
        }
        if (book.getDateOfPublish() != 0) {
            updatedBook.setDateOfPublish(book.getDateOfPublish());
        }
        if (book.getAuthors() != null) {
            updatedBook.setAuthors(book.getAuthors());
        }
        if (book.getGenres() != null) {
            updatedBook.setGenres(book.getGenres());
        }
        return new ResponseEntity<>(bookRepository.save(updatedBook), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/books/deleteById/{id}")
    public ResponseEntity<HttpStatus> deleteBookById(@PathVariable String id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/books/deleteAll")
    public ResponseEntity<HttpStatus> deleteAllBooks() {
        bookRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/books/borrowBook/{bookId}")
    @PreAuthorize("hasRole('ADMIN')or hasRole('USER')")
    public ResponseEntity<MessageResponse> borrowBook(@PathVariable String bookId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Book book = optionalBook.get();
        if (book.getBelongsToUser().compareTo("available") != 0) {
            System.out.println("here");
            return new ResponseEntity<>(new MessageResponse(""), HttpStatus.FORBIDDEN);
        } else {
            book.setBelongsToUser(username);
            book.setReturnDate(LocalDate.now().plusDays(20));
            bookRepository.save(book);
            return new ResponseEntity<>(new MessageResponse("Book" + book.getTitle() + " was borrowed to User " + username), HttpStatus.OK);
        }

    }

    @GetMapping("/books/returnBook/{bookId}")
    @PreAuthorize("hasRole('ADMIN')or hasRole('USER')")
    public ResponseEntity<MessageResponse> returnBook(@PathVariable String bookId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Book book = optionalBook.get();
        if (book.getBelongsToUser().compareTo(username) != 0) {

            return new ResponseEntity<>(new MessageResponse(""), HttpStatus.FORBIDDEN);
        } else {
            book.setBelongsToUser("available");
            book.setReturnDate(null);
            bookRepository.save(book);
            return new ResponseEntity<>(new MessageResponse("Book " + book.getTitle() + " was returned by User " + username), HttpStatus.OK);
        }

    }

    @GetMapping("/books/myBooks")
    @PreAuthorize("hasRole('ADMIN')or hasRole('USER')")
    public ResponseEntity<List<Book>> getMyBooks() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        List<Book> userBooks = bookRepository.findByBelongsToUser(username);
        Comparator<Book> mapComparator = (Book m1, Book m2) -> m1.getReturnDate().compareTo(m2.getReturnDate());
        Collections.sort(userBooks, mapComparator);

        if (!userBooks.isEmpty()) {
            return new ResponseEntity<>(userBooks, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/books/myLateBooks")
    @PreAuthorize("hasRole('ADMIN')or hasRole('USER')")
    public ResponseEntity<List<Book>> getMyLateBooks() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        List<Book> userBooks = bookRepository.findByBelongsToUser(username);
        List<Book> delayedBooks = new ArrayList<>();
        LocalDate now = LocalDate.now();
        for (int i = 0; i < userBooks.size(); i++) {
            Book book = userBooks.get(i);

            if (book.getReturnDate().isBefore(now)) {
                delayedBooks.add(book);
            }
            Comparator<Book> mapComparator = (Book m1, Book m2) -> m1.getReturnDate().compareTo(m2.getReturnDate());
            Collections.sort(delayedBooks, mapComparator);

        }
        if (!delayedBooks.isEmpty()) {
            return new ResponseEntity<>(delayedBooks, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/books/userBooks")
    public ResponseEntity<List<Book>> getUserBooks() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Book> userBooks = bookRepository.findByBelongsToUser(userDetails.getUsername());
        System.out.println("here");
        if (!userBooks.isEmpty()) {
            return new ResponseEntity<>(userBooks, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/books/checkForDelayed")
    public ResponseEntity<List<Book>> checkForDelayed() {
        System.out.println("checking");
        List<Book> controlledBooks = bookRepository.findByBelongsToUserIsNot("available");
        List<Book> delayedBooks = new ArrayList<>();
        System.out.println(controlledBooks);
        if (controlledBooks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        LocalDate now = LocalDate.now();
        for (int i = 0; i < controlledBooks.size(); i++) {
            Book book = controlledBooks.get(i);

            if (book.getReturnDate().isBefore(now)) {
                delayedBooks.add(book);
            }
            Comparator<Book> mapComparator = (Book m1, Book m2) -> m1.getReturnDate().compareTo(m2.getReturnDate());
            Collections.sort(delayedBooks, mapComparator);

        }
        if (!delayedBooks.isEmpty()) {
            return new ResponseEntity<>(delayedBooks, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
