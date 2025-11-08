package com.meridian.api.author;

public class AuthorNotFoundException extends RuntimeException {

    private String message;

    public AuthorNotFoundException(String message) {

        super(message);
        this.message = message;
    }
}
