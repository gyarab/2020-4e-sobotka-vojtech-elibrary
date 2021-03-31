package com.example.elibrary_final.controller;

import com.example.elibrary_final.model.Genre;
import com.example.elibrary_final.repository.GenreRepository;
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
public class GenreController {
    @Autowired
    GenreRepository genreRepository;
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/genres")
    public ResponseEntity<? extends Object> createGenre(@RequestBody Genre genre) {
        if (genreRepository.existsByName(genre.getName())){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        if (!genre.getName().isEmpty()) {
            return new ResponseEntity<>(genreRepository.save(genre), HttpStatus.OK);
        } else {
            return new ResponseEntity<MessageResponse>(new MessageResponse("missing Genre title"), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/genres")
    public ResponseEntity<? extends Object> findGenresById(@RequestParam(required = false) String id) {
        // if id is null Method return all Genres
        if (id == null) {
            List<Genre> Genres = genreRepository.findAll();
            if (!Genres.isEmpty()) {
                return new ResponseEntity<List<Genre>>(Genres, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

        } else {
            Optional<Genre> optionalGenre = genreRepository.findById(id);

            if (!optionalGenre.isEmpty()) {
                Genre genre = optionalGenre.get();
                return new ResponseEntity<>(genre, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }

    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/genres")
    public ResponseEntity<Genre> updateGenre(@RequestParam String id) {
        Optional<Genre> optionalGenre = genreRepository.findById(id);
        if (optionalGenre.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Genre Genre = optionalGenre.get();
        Genre updatedGenre = new Genre();
        if (Genre.getName() != null) {
            updatedGenre.setName(Genre.getName());
        }

        return new ResponseEntity<>(genreRepository.save(updatedGenre), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/genres")
    public ResponseEntity<HttpStatus> deleteGenreById(@RequestParam String id) {
        if (genreRepository.existsById(id)) {
            genreRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/genres/deleteAll")
    public ResponseEntity<HttpStatus> deleteAllGenres() {
        genreRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);

    }

}
