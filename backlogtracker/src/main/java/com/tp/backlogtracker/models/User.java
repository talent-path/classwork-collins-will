package com.tp.backlogtracker.models;

import java.util.List;

public class User {
    Integer userID;
    String name;
    List<Game> library;

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Game> getLibrary() {
        return library;
    }

    public void setLibrary(List<Game> library) {
        this.library = library;
    }
}
