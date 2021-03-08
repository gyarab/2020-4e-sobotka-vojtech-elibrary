package com.example.elibrary.service;

import com.example.elibrary.model.Author;
import com.example.elibrary.model.Genre;
import com.example.elibrary.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class GenreService {
    @Autowired
    GenreRepository genreRepository;

    public Genre addGenre(Genre genre) {
        List<Genre> possibleGenres = genreRepository.findByGenreNameContaining(genre.getGenreName());
        if (possibleGenres.get(0).getGenreName() != genre.getGenreName()) {
            Genre _genre = new Genre(genre.getGenreName());
            return genreRepository.save(_genre);
        } else {
            return possibleGenres.get(0);
        }
    }

    public List<Genre> getGenre() {
        List<Genre> authors = genreRepository.findAll();
        return authors;
    }

    public Genre getGenreByID(String id) {
        Optional<Genre> optionalAuthor = genreRepository.findById(id);
        Genre author = optionalAuthor.get();
        return author;
    }

    public void deleteGenre(String id) {
        genreRepository.deleteById(id);
    }

    public void deleteAllGenre() {
        genreRepository.deleteAll();
    }

    public List<Genre> findGenreByName(String string) {
        List<Genre> genreList = genreRepository.findByGenreNameContaining(string);
        return genreList;
    }

    public Genre updateGenre(Genre genre, String id) {
        Optional<Genre> optionalGenre = genreRepository.findById(id);
        Genre _genre = optionalGenre.get();
        _genre.setGenreName(genre.getGenreName());
        return genreRepository.save(_genre);
    }

    public List<Genre> findGenresFromListOfIds(List<String> ids) {
        System.out.println(ids.size());
        List<Genre> genreList = new ArrayList<>();
        for (
                int i = 0; i < ids.size(); i++) {
            genreList.add(getGenreByID(ids.get(i)));
        }
        return genreList;
    }
}
