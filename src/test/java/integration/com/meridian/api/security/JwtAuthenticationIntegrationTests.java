package integration.com.meridian.api.security;

import integration.com.meridian.base.BaseTest;
import integration.com.meridian.helper.jwt.JwtTestHelper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static org.hamcrest.Matchers.equalTo;

public class JwtAuthenticationIntegrationTests extends BaseTest {

    // Users endpoint tests
    
    @Test
    void createUser_withoutAuthorizationHeader_returns401() {
        String body = """
                {
                	"firstName": "Testing",
                	"lastName": "Name"
                }
                """;

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .post("/users");

        response.then().statusCode(401);
    }

    @Test
    void createUser_withInvalidToken_returns401() {
        String body = """
                {
                	"firstName": "Testing",
                	"lastName": "Name"
                }
                """;

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer invalid.token.here")
                .body(body)
                .post("/users");

        response.then().statusCode(401);
    }

    @Test
    void createUser_withExpiredToken_returns401() {
        String expiredToken = generateExpiredToken();
        String body = """
                {
                	"firstName": "Testing",
                	"lastName": "Name"
                }
                """;

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + expiredToken)
                .body(body)
                .post("/users");

        response.then().statusCode(401);
    }

    @Test
    void getAllUsers_withoutAuthorizationHeader_returns401() {
        Response response = RestAssured.given()
                .get("/users");

        response.then().statusCode(401);
    }

    @Test
    void getAllUsers_withInvalidToken_returns401() {
        Response response = RestAssured.given()
                .header("Authorization", "Bearer invalid.token.here")
                .get("/users");

        response.then().statusCode(401);
    }

    @Test
    void getUserById_withoutAuthorizationHeader_returns401() {
        Response response = RestAssured.given()
                .get("/users/1");

        response.then().statusCode(401);
    }

    @Test
    void getUserById_withInvalidToken_returns401() {
        Response response = RestAssured.given()
                .header("Authorization", "Bearer invalid.token.here")
                .get("/users/1");

        response.then().statusCode(401);
    }

    @Test
    void updateUser_withoutAuthorizationHeader_returns401() {
        String body = """
                {
                	"firstName": "Updated",
                	"lastName": "Name"
                }
                """;

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .put("/users/1");

        response.then().statusCode(401);
    }

    @Test
    void updateUser_withInvalidToken_returns401() {
        String body = """
                {
                	"firstName": "Updated",
                	"lastName": "Name"
                }
                """;

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer invalid.token.here")
                .body(body)
                .put("/users/1");

        response.then().statusCode(401);
    }

    @Test
    void deleteUser_withoutAuthorizationHeader_returns401() {
        Response response = RestAssured.given()
                .delete("/users/1");

        response.then().statusCode(401);
    }

    @Test
    void deleteUser_withInvalidToken_returns401() {
        Response response = RestAssured.given()
                .header("Authorization", "Bearer invalid.token.here")
                .delete("/users/1");

        response.then().statusCode(401);
    }

    // Account endpoint tests

    @Test
    void createAccount_withoutAuthorizationHeader_returns401() {
        String body = """
                {
                	"accountType": "TRADING"
                }
                """;

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .post("/users/1/accounts");

        response.then().statusCode(401);
    }

    @Test
    void createAccount_withInvalidToken_returns401() {
        String body = """
                {
                	"accountType": "TRADING"
                }
                """;

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer invalid.token.here")
                .body(body)
                .post("/users/1/accounts");

        response.then().statusCode(401);
    }

    @Test
    void getAllAccounts_withoutAuthorizationHeader_returns401() {
        Response response = RestAssured.given()
                .get("/users/1/accounts");

        response.then().statusCode(401);
    }

    @Test
    void getAllAccounts_withInvalidToken_returns401() {
        Response response = RestAssured.given()
                .header("Authorization", "Bearer invalid.token.here")
                .get("/users/1/accounts");

        response.then().statusCode(401);
    }

    @Test
    void updateAccount_withoutAuthorizationHeader_returns401() {
        String body = """
                {
                	"accountType": "CREDIT"
                }
                """;

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .put("/accounts/1");

        response.then().statusCode(401);
    }

    @Test
    void updateAccount_withInvalidToken_returns401() {
        String body = """
                {
                	"accountType": "CREDIT"
                }
                """;

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer invalid.token.here")
                .body(body)
                .put("/accounts/1");

        response.then().statusCode(401);
    }

    @Test
    void deleteAccount_withoutAuthorizationHeader_returns401() {
        Response response = RestAssured.given()
                .delete("/accounts/1");

        response.then().statusCode(401);
    }

    @Test
    void deleteAccount_withInvalidToken_returns401() {
        Response response = RestAssured.given()
                .header("Authorization", "Bearer invalid.token.here")
                .delete("/accounts/1");

        response.then().statusCode(401);
    }

    // Helper method to generate expired token
    private String generateExpiredToken() {
        String secret = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        
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
