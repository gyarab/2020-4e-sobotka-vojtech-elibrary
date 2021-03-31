package com.example.elibrary_final.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Document("books")
public class Book {
    @Id
    private String id;

    private String title;
    private String description;
    private int dateOfPublish;
    private List<String> authors;
    private List<String> genres;
    private String belongsToUser;
    private LocalDate returnDate;

    public Book() {

    }

    public Book(String title, String description, int dateOfPublish, List<String> authors, List<String> genres) {
        this.title = title;
        this.description = description;
        this.dateOfPublish = dateOfPublish;
        this.authors = authors;
        this.genres = genres;
    }

    public String getId() {
        return id;
    }

    public String getBelongsToUser() {
        return belongsToUser;
    }

    public void setBelongsToUser(String belongsToUser) {
        this.belongsToUser = belongsToUser;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate    returnDate) {
        this.returnDate = returnDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDateOfPublish() {
        return dateOfPublish;
    }

    public void setDateOfPublish(int dateOfPublish) {
        this.dateOfPublish = dateOfPublish;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

}