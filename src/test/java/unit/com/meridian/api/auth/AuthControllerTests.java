package unit.com.meridian.api.auth;

import com.meridian.api.auth.AuthController;
import com.meridian.api.auth.AuthDTO;
import com.meridian.api.auth.AuthResponse;
import com.meridian.api.auth.AuthService;
import com.meridian.api.errors.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTests {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @Test
    void authenticate_shouldReturnToken_whenCredentialsAreValid() {
        AuthDTO authDTO = new AuthDTO();
        authDTO.setUsername("testuser");
        authDTO.setPassword("TestPass123!");
        
        String expectedToken = "jwt.token.here";
        when(authService.authenticate("testuser", "TestPass123!"))
                .thenReturn(Optional.of(expectedToken));

        ResponseEntity<?> result = authController.authenticate(authDTO);

        assertEquals(200, result.getStatusCode().value());
        assertNotNull(result.getBody());
        assertTrue(result.getBody() instanceof AuthResponse);
        AuthResponse authResponse = (AuthResponse) result.getBody();
        assertEquals(expectedToken, authResponse.getToken());
        verify(authService).authenticate("testuser", "TestPass123!");
    }

    @Test
    void authenticate_shouldReturn401_whenCredentialsAreInvalid() {
        AuthDTO authDTO = new AuthDTO();
        authDTO.setUsername("testuser");
        authDTO.setPassword("WrongPassword123!");
        
        when(authService.authenticate("testuser", "WrongPassword123!"))
                .thenReturn(Optional.empty());

        ResponseEntity<?> result = authController.authenticate(authDTO);

        assertEquals(401, result.getStatusCode().value());
        assertNotNull(result.getBody());
        assertTrue(result.getBody() instanceof ErrorResponse);
        ErrorResponse errorResponse = (ErrorResponse) result.getBody();
        assertEquals(401, errorResponse.getStatus());
        assertEquals("Unauthorized", errorResponse.getError());
        assertEquals("Invalid username or password", errorResponse.getMessage());
        verify(authService).authenticate("testuser", "WrongPassword123!");
    }

    @Test
    void authenticate_shouldReturn401_whenUsernameDoesNotExist() {
        AuthDTO authDTO = new AuthDTO();
        authDTO.setUsername("nonexistentuser");
        authDTO.setPassword("TestPass123!");
        
        when(authService.authenticate("nonexistentuser", "TestPass123!"))
                .thenReturn(Optional.empty());

        ResponseEntity<?> result = authController.authenticate(authDTO);

        assertEquals(401, result.getStatusCode().value());
        assertNotNull(result.getBody());
        assertTrue(result.getBody() instanceof ErrorResponse);
        ErrorResponse errorResponse = (ErrorResponse) result.getBody();
        assertEquals("Invalid username or password", errorResponse.getMessage());
        verify(authService).authenticate("nonexistentuser", "TestPass123!");
    }

    @Test
    void authenticate_shouldCallAuthService_withCorrectParameters() {
        AuthDTO authDTO = new AuthDTO();
        authDTO.setUsername("user123");
        authDTO.setPassword("Pass456!");
        
        when(authService.authenticate("user123", "Pass456!"))
                .thenReturn(Optional.of("token123"));

        authController.authenticate(authDTO);

        verify(authService, times(1)).authenticate("user123", "Pass456!");
    }

    @Test
    void authenticate_shouldReturnErrorResponse_withGenericMessage() {
        AuthDTO authDTO = new AuthDTO();
        authDTO.setUsername("anyuser");
        authDTO.setPassword("anypass");
        
        when(authService.authenticate(anyString(), anyString()))
                .thenReturn(Optional.empty());

        ResponseEntity<?> result = authController.authenticate(authDTO);

        ErrorResponse errorResponse = (ErrorResponse) result.getBody();
        assertEquals("Invalid username or password", errorResponse.getMessage());
        // Verify the message is generic and doesn't reveal specific details
        assertTrue(errorResponse.getMessage().contains("Invalid"));
    }
}
