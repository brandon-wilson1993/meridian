package integration.com.meridian.helper.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JwtTokenHelper {

    private static final String SECRET = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

    /**
     * Generate an expired JWT token for testing authentication failures
     * @return An expired JWT token
     */
    public static String generateExpiredToken() {
        SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
        
        Date now = new Date();
        Date expiredDate = new Date(now.getTime() - 3600000); // 1 hour ago
        
        return Jwts.builder()
                .subject("testuser")
                .issuedAt(new Date(now.getTime() - 7200000)) // 2 hours ago
                .expiration(expiredDate)
                .signWith(key)
                .compact();
    }
}
