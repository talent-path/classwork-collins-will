package com.tp.library.models;

import java.util.ArrayList;
import java.util.List;

public class Book {

    String title;
    List<String> authors;
    Integer year;
    Integer id;

    public Book(String title, List<String> authors, Integer year, Integer id) {
        this.title = title;
        this.authors = authors;
        this.year = year;
        this.id = id;
    }

    public Book(Book that) {
        this.title = that.title;
        this.authors = new ArrayList<>();
        for (String author : that.getAuthors()) {
            this.authors.add(author);
        }
        this.year = that.year;
        this.id = that.id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
