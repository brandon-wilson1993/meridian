package com.meridian.api.auth;

import java.util.Optional;

public interface AuthService {

    Optional<String> authenticate(String username, String password);
}
