package com.tp.connectfour.exceptions;

public class NullGameIDException extends Exception{

    public NullGameIDException(String message) {
        super(message);
    }

    public NullGameIDException(String message, Throwable innerException) {
        super(message, innerException);
    }
}
