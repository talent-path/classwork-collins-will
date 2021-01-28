package com.tp.connectfour.exceptions;

public class GameOverException extends Exception {

    public GameOverException(String message) {
        super(message);
    }

    public GameOverException(String message, Throwable innerException) {
        super(message, innerException);
    }
}
