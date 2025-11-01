package com.meridian.api.authors;

public class AuthorNotFoundException extends RuntimeException {

    public AuthorNotFoundException(Long id) {

        super("Could not find Platform " + id);
    }
}
