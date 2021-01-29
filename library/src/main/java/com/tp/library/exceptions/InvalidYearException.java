package com.tp.library.exceptions;

public class InvalidYearException extends Exception {

    public InvalidYearException(String message) {
        super(message);
    }

    public InvalidYearException(String message, Throwable innerException) {
        super(message, innerException);
    }

}
