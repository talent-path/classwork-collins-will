package com.tp.library.exceptions;

public class InvalidQueryException extends Exception {

    public InvalidQueryException(String message) {
        super(message);
    }

    public InvalidQueryException(String message, Throwable innerException) {
        super(message, innerException);
    }
}
