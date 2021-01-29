package com.tp.library.controllers;

import java.util.List;

public class ChangeRequest {
    String newTitle;
    List<String> newAuthors;
    Integer newYear;

    public List<String> getNewAuthors() {
        return newAuthors;
    }

    public void setNewAuthors(List<String> newAuthors) {
        this.newAuthors = newAuthors;
    }

    public String getNewTitle() {
        return newTitle;
    }

    public void setNewTitle(String newTitle) {
        this.newTitle = newTitle;
    }

    public Integer getNewYear() {
        return newYear;
    }

    public void setNewYear(Integer newYear) {
        this.newYear = newYear;
    }
}
