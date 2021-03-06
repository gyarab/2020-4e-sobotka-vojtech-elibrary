package com.example.elibrary.model;

import hirondelle.date4j.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Document(collection = "books")
public class Book {
    @Id
    private String id;
    @NotNull(message = "Book title is required")
    private String title;
    private String description;
    private int yearOfPublish;
    private List<Genre> genres;
    private List<Author> authors;
    private List<String> authorIds;
    private List<String> genresIds;
    private String borrowedByUserId;
    private DateTime returnDate;

    public Book(String title, String description, int yearOfPublish, List<String> genresIds, List<String> authorIds) {
        this.title = title;
        this.description = description;
        this.yearOfPublish = yearOfPublish;
        this.genresIds = genresIds;
        this.authorIds = authorIds;
        this.setBorrowedByUserId("avalaible");
    }

    public String getTitle() {
        return title;
    }

    public List<String> getGenresIds() {
        return genresIds;
    }

    public void setGenresIds(List<String> genresIds) {
        this.genresIds = genresIds;
    }

    public List<String> getAuthorIds() {
        return authorIds;
    }

    public String getBorrowedByUserId() {
        return borrowedByUserId;
    }

    public void setBorrowedByUserId(String borrowedByUserId) {
        this.borrowedByUserId = borrowedByUserId;
    }

    public DateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(DateTime returnDate) {
        this.returnDate = returnDate;
    }

    public void setAuthorIds(List<String> authorIds) {
        this.authorIds = authorIds;
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

    public int getYearOfPublish() {
        return yearOfPublish;
    }

    public void setYearOfPublish(int yearOfPublish) {
        this.yearOfPublish = yearOfPublish;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public String getId() {
        return id;
    }
}
