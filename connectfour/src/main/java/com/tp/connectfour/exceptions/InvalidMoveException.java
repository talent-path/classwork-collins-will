package com.tp.connectfour.exceptions;

public class InvalidMoveException extends Exception {

    public InvalidMoveException(String message) {
        super(message);
    }

    public InvalidMoveException(String message, Throwable innerException) {
        super(message, innerException);
    }
}
