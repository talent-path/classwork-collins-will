package com.tp.hangman.services;

public class InvalidGameIdException extends Exception {

    public InvalidGameIdException(String message) {
        super(message);
    }

    public InvalidGameIdException(String message, Throwable innerException) {
        super(message, innerException);
    }
}
