package com.tp.backlogtracker.models;

public class Game {

    int gameID;
    String name;
    double playTime;
    boolean completed;

    public Game(int gameID, String name, double playTime, boolean completed) {
        this.gameID = gameID;
        this.name = name;
        this.playTime = playTime;
        this.completed = completed;
    }

    public Game(Game that) {
        this.gameID = that.gameID;
        this.name = that.name;
        this.playTime = that.playTime;
        this.completed = that.completed;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPlayTime() {
        return playTime;
    }

    public void setPlayTime(double playTime) {
        this.playTime = playTime;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
