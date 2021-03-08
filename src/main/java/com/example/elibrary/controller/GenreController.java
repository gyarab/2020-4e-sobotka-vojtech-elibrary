package com.example.elibrary.controller;

import com.example.elibrary.model.Genre;
import com.example.elibrary.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("/api")
public class GenreController {
    @Autowired
    public GenreService GenreService;

    @PostMapping("/genres")
    public ResponseEntity<Genre> addGenre(@RequestBody Genre genre) {
        return new ResponseEntity<>(GenreService.addGenre(genre), HttpStatus.OK);
    }

    @GetMapping("/genres")
    public ResponseEntity<List<Genre>> getGenres() {
        List<Genre> Genres = GenreService.getGenre();
        if (Genres.isEmpty() == false) {
            return new ResponseEntity<>(Genres, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

    }

    @GetMapping("/genres/{id}")
    public ResponseEntity<Genre> getGenreById(@PathVariable("id") String id) {
        Genre Genre = GenreService.getGenreByID(id);
        return new ResponseEntity<>(Genre, HttpStatus.OK);
    }

    @GetMapping("/genresByName/{string}")
    public ResponseEntity<List<Genre>> getGenreByName(@PathVariable("string") String string) {
        List<Genre> GenreList = GenreService.findGenreByName(string);
        if (GenreList.isEmpty() == false) {
            return new ResponseEntity<List<Genre>>(GenreList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/genres/{id}")
    public ResponseEntity<Genre> updateBook(@RequestBody Genre Genre, @PathVariable("id") String id) {
        return new ResponseEntity<>(GenreService.updateGenre(Genre, id), HttpStatus.OK);
    }


    @DeleteMapping("/genres/{id}")
    public void deleteGenre(@PathVariable("id") String id) {
        GenreService.deleteGenre(id);
    }


    @DeleteMapping("/genres")
    public void deleteAllGenres() {
        GenreService.deleteAllGenre();
    }
}
