package unit.com.meridian.api.auth;

import com.meridian.api.auth.AuthResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AuthResponseTests {

    @Test
    void authResponse_getToken() {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.test.token";

        AuthResponse authResponse = new AuthResponse(token);

        assertEquals(token, authResponse.getToken());
    }

    @Test
    void authResponse_withNullToken() {
        AuthResponse authResponse = new AuthResponse(null);

        assertNull(authResponse.getToken());
    }

    @Test
    void authResponse_withEmptyToken() {
        AuthResponse authResponse = new AuthResponse("");

        assertEquals("", authResponse.getToken());
    }

    @Test
    void authResponse_tokenIsImmutable() {
        String originalToken = "original.jwt.token";
        AuthResponse authResponse = new AuthResponse(originalToken);

        String retrievedToken = authResponse.getToken();

        assertEquals(originalToken, retrievedToken);
        // Verify token value doesn't change
        assertEquals(originalToken, authResponse.getToken());
    }

    @Test
    void authResponse_withLongToken() {
        String longToken = "Bearer." + "a".repeat(500);

        AuthResponse authResponse = new AuthResponse(longToken);

        assertEquals(longToken, authResponse.getToken());
        assertEquals(507, authResponse.getToken().length());
    }

    @Test
    void authResponse_withSpecialCharacters() {
        String tokenWithSpecialChars = "token.with-special_chars+/=";

        AuthResponse authResponse = new AuthResponse(tokenWithSpecialChars);

        assertEquals(tokenWithSpecialChars, authResponse.getToken());
    }

    @Test
    void authResponse_multipleInstances_shouldBeIndependent() {
        AuthResponse response1 = new AuthResponse("token1");
        AuthResponse response2 = new AuthResponse("token2");

        assertEquals("token1", response1.getToken());
        assertEquals("token2", response2.getToken());
        assertNotEquals(response1.getToken(), response2.getToken());
    }
}
