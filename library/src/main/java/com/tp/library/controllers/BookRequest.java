package com.tp.library.controllers;

import java.util.List;

public class BookRequest {
    private String title;
    private List<String> authors;
    private Integer year;

    public String getTitle() {
        return title;
    }

    public void setName(String title) {
        this.title = title;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}