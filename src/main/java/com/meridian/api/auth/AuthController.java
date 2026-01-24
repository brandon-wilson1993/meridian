package com.meridian.api.auth;

import com.meridian.api.errors.ErrorResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/auth")
    public ResponseEntity<?> authenticate(@Valid @RequestBody AuthRequest authRequest) {
        Optional<String> tokenOptional = authService.authenticate(
                authRequest.getUsername(),
                authRequest.getPassword()
        );

        if (tokenOptional.isEmpty()) {
            ErrorResponse errorResponse = new ErrorResponse(
                    HttpStatus.UNAUTHORIZED.value(),
                    "Unauthorized",
                    "Invalid username or password"
            );
            return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
        }

        AuthResponse authResponse = new AuthResponse(tokenOptional.get());
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }
}
