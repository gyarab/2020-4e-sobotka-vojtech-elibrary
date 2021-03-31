package com.example.elibrary_final.service;

public class FilterReq {

    private String[] authors;
    private String[] genres;


    public FilterReq(String[] authors, String[] genres, boolean available) {
        this.authors = authors;
        this.genres = genres;

    }

    public String[] getAuthors() {
        return authors;
    }

    public void setAuthors(String[] authors) {
        this.authors = authors;
    }

    public String[] getGenres() {
        return genres;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }
}
