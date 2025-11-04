package com.meridian.api.author;

public class AuthorNotFoundException extends RuntimeException {

    public AuthorNotFoundException(Long id) {

        super("Could not find Platform " + id);
    }
}
