package com.test.error;

/**
 * Created by User on 29.01.2016.
 */
public class InvalidFileNameException extends Exception {
    public InvalidFileNameException() {
    }

    public InvalidFileNameException(String message) {
        super(message);
    }
}
