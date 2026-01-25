package unit.com.meridian.api.auth;

import com.meridian.api.auth.AuthService;
import com.meridian.api.auth.AuthServiceImpl;
import com.meridian.api.users.Users;
import com.meridian.api.users.UsersRepository;
import com.meridian.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTests {

    private static Users testUser;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeAll
    static void beforeAll() {
        testUser = new Users();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setPassword("$2a$10$hashedPassword");
        testUser.setFirstName("Test");
        testUser.setLastName("User");
    }

    @Test
    void authenticate_shouldReturnToken_whenCredentialsAreValid() {
        String username = "testuser";
        String password = "TestPass123!";
        String expectedToken = "jwt.token.here";

        when(usersRepository.findByUsername(username)).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches(password, testUser.getPassword())).thenReturn(true);
        when(jwtTokenProvider.generateToken(username)).thenReturn(expectedToken);

        Optional<String> result = authService.authenticate(username, password);

        assertTrue(result.isPresent());
        assertEquals(expectedToken, result.get());
        verify(usersRepository).findByUsername(username);
        verify(passwordEncoder).matches(password, testUser.getPassword());
        verify(jwtTokenProvider).generateToken(username);
    }

    @Test
    void authenticate_shouldReturnEmpty_whenUsernameNotFound() {
        String username = "nonexistent";
        String password = "TestPass123!";

        when(usersRepository.findByUsername(username)).thenReturn(Optional.empty());

        Optional<String> result = authService.authenticate(username, password);

        assertFalse(result.isPresent());
        verify(usersRepository).findByUsername(username);
        // Verify dummy password check is performed to prevent timing attacks
        verify(passwordEncoder).matches(eq(password), anyString());
        verify(jwtTokenProvider, never()).generateToken(any());
    }

    @Test
    void authenticate_shouldReturnEmpty_whenPasswordIsIncorrect() {
        String username = "testuser";
        String password = "WrongPassword123!";

        when(usersRepository.findByUsername(username)).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches(password, testUser.getPassword())).thenReturn(false);

        Optional<String> result = authService.authenticate(username, password);

        assertFalse(result.isPresent());
        verify(usersRepository).findByUsername(username);
        verify(passwordEncoder).matches(password, testUser.getPassword());
        verify(jwtTokenProvider, never()).generateToken(any());
    }

    @Test
    void authenticate_shouldReturnEmpty_whenPasswordIsNull() {
        String username = "testuser";
        String password = null;

        when(usersRepository.findByUsername(username)).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches(password, testUser.getPassword())).thenReturn(false);

        Optional<String> result = authService.authenticate(username, password);

        assertFalse(result.isPresent());
        verify(usersRepository).findByUsername(username);
        verify(passwordEncoder).matches(password, testUser.getPassword());
        verify(jwtTokenProvider, never()).generateToken(any());
    }

    @Test
    void authenticate_shouldReturnEmpty_whenUsernameIsNull() {
        String username = null;
        String password = "TestPass123!";

        when(usersRepository.findByUsername(username)).thenReturn(Optional.empty());

        Optional<String> result = authService.authenticate(username, password);

        assertFalse(result.isPresent());
        verify(usersRepository).findByUsername(username);
        // Verify dummy password check is performed to prevent timing attacks
        verify(passwordEncoder).matches(eq(password), anyString());
        verify(jwtTokenProvider, never()).generateToken(any());
    }
}
