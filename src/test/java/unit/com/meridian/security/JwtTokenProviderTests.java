package unit.com.meridian.security;

import com.meridian.security.JwtAuthenticationException;
import com.meridian.security.JwtTokenProvider;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class JwtTokenProviderTests {

    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        jwtTokenProvider = new JwtTokenProvider();
        ReflectionTestUtils.setField(jwtTokenProvider, "jwtSecret", 
            "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970");
        ReflectionTestUtils.setField(jwtTokenProvider, "jwtExpirationMs", 86400000L);
    }

    @Test
    void generateToken_shouldGenerateValidToken() {
        String username = "testuser";
        
        String token = jwtTokenProvider.generateToken(username);
        
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void getUsernameFromToken_shouldReturnCorrectUsername() {
        String username = "testuser";
        String token = jwtTokenProvider.generateToken(username);
        
        String extractedUsername = jwtTokenProvider.getUsernameFromToken(token);
        
        assertEquals(username, extractedUsername);
    }

    @Test
    void validateToken_shouldReturnTrueForValidToken() {
        String token = jwtTokenProvider.generateToken("testuser");
        
        boolean isValid = jwtTokenProvider.validateToken(token);
        
        assertTrue(isValid);
    }

    @Test
    void validateToken_shouldReturnFalseForInvalidToken() {
        String invalidToken = "invalid.token.here";
        
        boolean isValid = jwtTokenProvider.validateToken(invalidToken);
        
        assertFalse(isValid);
    }

    @Test
    void validateToken_shouldReturnFalseForEmptyToken() {
        String emptyToken = "";
        
        boolean isValid = jwtTokenProvider.validateToken(emptyToken);
        
        assertFalse(isValid);
    }

    @Test
    void validateToken_shouldReturnFalseForNullToken() {
        boolean isValid = jwtTokenProvider.validateToken(null);
        
        assertFalse(isValid);
    }

    @Test
    void getUsernameFromToken_shouldThrowExceptionForInvalidToken() {
        String invalidToken = "invalid.token.here";
        
        assertThrows(JwtAuthenticationException.class, () -> {
            jwtTokenProvider.getUsernameFromToken(invalidToken);
        });
    }

    @Test
    void validateToken_shouldReturnFalseForExpiredToken() {
        // Create an expired token (expired 1 hour ago)
        String secret = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        
        Date now = new Date();
        Date expiredDate = new Date(now.getTime() - 3600000); // 1 hour ago
        
        String expiredToken = Jwts.builder()
                .subject("testuser")
                .issuedAt(new Date(now.getTime() - 7200000)) // 2 hours ago
                .expiration(expiredDate)
                .signWith(key)
                .compact();
        
        boolean isValid = jwtTokenProvider.validateToken(expiredToken);
        
        assertFalse(isValid);
    }

    @Test
    void validateToken_shouldReturnFalseForTokenWithInvalidSignature() {
        // Create a token with a different secret key
        String differentSecret = "DifferentSecretKeyForTestingPurposes12345678901234567890";
        SecretKey differentKey = Keys.hmacShaKeyFor(differentSecret.getBytes(StandardCharsets.UTF_8));
        
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 86400000);
        
        String tokenWithWrongSignature = Jwts.builder()
                .subject("testuser")
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(differentKey)
                .compact();
        
        boolean isValid = jwtTokenProvider.validateToken(tokenWithWrongSignature);
        
        assertFalse(isValid);
    }

    @Test
    void validateToken_shouldReturnFalseForUnsupportedToken() {
        // Create a token with unsupported format (e.g., unsigned token)
        String unsupportedToken = Jwts.builder()
                .subject("testuser")
                .compact(); // No signature
        
        boolean isValid = jwtTokenProvider.validateToken(unsupportedToken);
        
        assertFalse(isValid);
    }
}
