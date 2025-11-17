package com.meridian.api.users;

public class UsersNotFoundException extends RuntimeException {

    private String message;

    public UsersNotFoundException(String message) {

        super(message);
        this.message = message;
    }
}
