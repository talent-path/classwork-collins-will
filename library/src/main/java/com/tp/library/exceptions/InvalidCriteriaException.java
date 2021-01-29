package com.tp.library.exceptions;

public class InvalidCriteriaException extends Exception {

    public InvalidCriteriaException(String message) {
        super(message);
    }

    public InvalidCriteriaException(String message, Throwable innerException) {
        super(message, innerException);
    }
}
