package com.meridian.api.auth;

import com.meridian.api.users.Users;
import com.meridian.api.users.UsersRepository;
import com.meridian.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public Optional<String> authenticate(String username, String password) {
        Optional<Users> userOptional = usersRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            // Perform dummy password check to prevent timing attacks
            passwordEncoder.matches(password, "$2a$10$dummyHashToPreventTimingAttack1234567890123456789012");
            return Optional.empty();
        }

        Users user = userOptional.get();
        
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return Optional.empty();
        }

        String token = jwtTokenProvider.generateToken(user.getUsername());
        return Optional.of(token);
    }
}
