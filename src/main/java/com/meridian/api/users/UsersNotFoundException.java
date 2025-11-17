package com.meridian.api.users;

public class AuthorNotFoundException extends RuntimeException {

    private String message;

    public AuthorNotFoundException(String message) {

        super(message);
        this.message = message;
    }
}
