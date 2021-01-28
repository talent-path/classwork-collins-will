package com.tp.connectfour.controllers;

public class MoveRequest {

    private Integer move;
    private Integer gameID;

    public Integer getMove() {
        return move;
    }

    public void setMove(Integer move) {
        this.move = move;
    }

    public Integer getGameID() {
        return gameID;
    }

    public void setGameID(Integer gameID) {
        this.gameID = gameID;
    }

}
