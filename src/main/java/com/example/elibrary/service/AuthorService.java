package com.example.elibrary.service;

import com.example.elibrary.model.Author;
import com.example.elibrary.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthorService {
    @Autowired
    AuthorRepository authorRepository;

    public Author addAuthor(Author author) {
        List<Author> possibleAuthors = authorRepository.findByName(author.getName());
        if (possibleAuthors.isEmpty()) {
            Author _author = new Author(author.getName(), author.getDescribtion());
            return authorRepository.save(_author);
        } else {
            return possibleAuthors.get(0);
        }

    }

    public List<Author> getAuthor() {
        List<Author> authors = authorRepository.findAll();
        return authors;
    }

    public Author getAuthorByID(String id) {
        Optional<Author> optionalAuthor = authorRepository.findById(id);
        Author author = optionalAuthor.get();
        return author;
    }

    public void deleteAuthor(String id) {
        authorRepository.deleteById(id);
    }

    public void deleteAllAuthor() {
        authorRepository.deleteAll();
    }

    public List<Author> findAuthorByName(String string) {
        List<Author> authorList = authorRepository.findByNameContaining(string);
        return authorList;
    }

    public Author updateAuthor(Author author, String id) {
        Optional<Author> optionalAuthor = authorRepository.findById(id);
        Author _author = optionalAuthor.get();
        if (author.getName() != null) {
            _author.setName(author.getName());
        }
        if (author.getDescribtion() != null) {
            _author.setDescribtion(author.getDescribtion());
        }
        return authorRepository.save(_author);
    }

    public List<Author> findAuthorsFromListOfIds(List<String> ids) {
        List<Author> authorList = new ArrayList<>();
        for (
                int i = 0; i < ids.size(); i++) {
            authorList.add(getAuthorByID(ids.get(i)));
        }
        return authorList;
    }
}

