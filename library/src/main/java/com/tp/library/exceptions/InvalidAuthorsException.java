package com.tp.library.exceptions;

public class InvalidAuthorsException extends Exception {

    public InvalidAuthorsException(String message) {
        super(message);
    }

    public InvalidAuthorsException(String message, Throwable innerException) {
        super(message, innerException);
    }

}
