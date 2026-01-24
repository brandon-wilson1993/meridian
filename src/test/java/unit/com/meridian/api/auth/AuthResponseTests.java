package unit.com.meridian.api.auth;

import com.meridian.api.auth.AuthResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AuthResponseTests {

    @Test
    void authResponse_getToken() {
        // Arrange
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.test.token";

        // Act
        AuthResponse authResponse = new AuthResponse(token);

        // Assert
        assertEquals(token, authResponse.getToken());
    }

    @Test
    void authResponse_withNullToken() {
        // Arrange & Act
        AuthResponse authResponse = new AuthResponse(null);

        // Assert
        assertNull(authResponse.getToken());
    }

    @Test
    void authResponse_withEmptyToken() {
        // Arrange & Act
        AuthResponse authResponse = new AuthResponse("");

        // Assert
        assertEquals("", authResponse.getToken());
    }

    @Test
    void authResponse_tokenIsImmutable() {
        // Arrange
        String originalToken = "original.jwt.token";
        AuthResponse authResponse = new AuthResponse(originalToken);

        // Act
        String retrievedToken = authResponse.getToken();

        // Assert
        assertEquals(originalToken, retrievedToken);
        // Verify token value doesn't change
        assertEquals(originalToken, authResponse.getToken());
    }

    @Test
    void authResponse_withLongToken() {
        // Arrange
        String longToken = "Bearer." + "a".repeat(500);

        // Act
        AuthResponse authResponse = new AuthResponse(longToken);

        // Assert
        assertEquals(longToken, authResponse.getToken());
        assertEquals(507, authResponse.getToken().length());
    }

    @Test
    void authResponse_withSpecialCharacters() {
        // Arrange
        String tokenWithSpecialChars = "token.with-special_chars+/=";

        // Act
        AuthResponse authResponse = new AuthResponse(tokenWithSpecialChars);

        // Assert
        assertEquals(tokenWithSpecialChars, authResponse.getToken());
    }

    @Test
    void authResponse_multipleInstances_shouldBeIndependent() {
        // Arrange & Act
        AuthResponse response1 = new AuthResponse("token1");
        AuthResponse response2 = new AuthResponse("token2");

        // Assert
        assertEquals("token1", response1.getToken());
        assertEquals("token2", response2.getToken());
        assertNotEquals(response1.getToken(), response2.getToken());
    }
}
