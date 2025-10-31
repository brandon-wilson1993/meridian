package com.meridian.api.authors;

public class AuthorsNotFoundException extends RuntimeException {

    public AuthorsNotFoundException(Long id) {

        super("Could not find Platform " + id);
    }
}
