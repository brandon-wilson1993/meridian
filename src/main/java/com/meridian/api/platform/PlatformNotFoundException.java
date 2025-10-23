package com.meridian.api.platform;

public class PlatformNotFoundException extends RuntimeException {

    public PlatformNotFoundException(Long id) {

        super("Could not find Platform " + id);
    }
}
