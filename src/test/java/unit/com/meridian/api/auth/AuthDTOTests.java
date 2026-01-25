package unit.com.meridian.api.auth;

import com.meridian.api.auth.AuthDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AuthDTOTests {

    @Test
    void authDTO_gettersAndSetters() {
        AuthDTO authDTO = new AuthDTO();
        authDTO.setUsername("testuser");
        authDTO.setPassword("TestPassword123!");

        assertEquals("testuser", authDTO.getUsername());
        assertEquals("TestPassword123!", authDTO.getPassword());
    }

    @Test
    void authDTO_shouldAllowNullValues() {
        AuthDTO authDTO = new AuthDTO();
        authDTO.setUsername(null);
        authDTO.setPassword(null);

        assertNull(authDTO.getUsername());
        assertNull(authDTO.getPassword());
    }

    @Test
    void authDTO_shouldAllowEmptyValues() {
        AuthDTO authDTO = new AuthDTO();
        authDTO.setUsername("");
        authDTO.setPassword("");

        assertEquals("", authDTO.getUsername());
        assertEquals("", authDTO.getPassword());
    }

    @Test
    void authDTO_shouldHandleSpecialCharacters() {
        AuthDTO authDTO = new AuthDTO();
        authDTO.setUsername("user@example.com");
        authDTO.setPassword("P@ssw0rd!#$");

        assertEquals("user@example.com", authDTO.getUsername());
        assertEquals("P@ssw0rd!#$", authDTO.getPassword());
    }

    @Test
    void authDTO_noArgsConstructor() {
        AuthDTO authDTO = new AuthDTO();

        assertNotNull(authDTO);
        assertNull(authDTO.getUsername());
        assertNull(authDTO.getPassword());
    }

    @Test
    void authDTO_shouldAllowLongValues() {
        String longUsername = "a".repeat(255);
        String longPassword = "P".repeat(255);
        
        AuthDTO authDTO = new AuthDTO();
        authDTO.setUsername(longUsername);
        authDTO.setPassword(longPassword);

        assertEquals(longUsername, authDTO.getUsername());
        assertEquals(longPassword, authDTO.getPassword());
        assertEquals(255, authDTO.getUsername().length());
        assertEquals(255, authDTO.getPassword().length());
    }
}
