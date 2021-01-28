package com.tp.hangman.services;

public class InvalidGuessException extends Exception {

    public InvalidGuessException(String message) {
        super(message);
    }

    public InvalidGuessException(String message, Throwable innerException) {
        super(message, innerException);
    }
}
