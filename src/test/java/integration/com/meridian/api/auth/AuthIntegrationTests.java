package integration.com.meridian.api.auth;

import integration.com.meridian.base.BaseTest;
import integration.com.meridian.helper.restassured.RequestType;
import integration.com.meridian.helper.restassured.RestAssuredHelpers;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

public class AuthIntegrationTests extends BaseTest {

    private static String testUsername;
    private static String testPassword;

    @BeforeAll
    static void setupTestUser() {
        // Create a test user with known credentials
        testUsername = "authtest" + RandomStringUtils.randomAlphabetic(8);
        testPassword = "TestPass123!";
        
        String userBody = """
                {
                    "firstName": "Auth",
                    "lastName": "Test",
                    "username": "%s",
                    "password": "%s"
                }
                """.formatted(testUsername, testPassword);
        
        RestAssuredHelpers.requestHelper("/users", RequestType.POST, userBody, jwtToken);
    }

    @Test
    void authenticate_withValidCredentials_returns200AndToken() {
        String body = """
                {
                    "username": "%s",
                    "password": "%s"
                }
                """.formatted(testUsername, testPassword);

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .post("/auth");

        response.then()
                .statusCode(200)
                .body("token", notNullValue())
                .body("token", not(emptyString()));
    }

    @Test
    void authenticate_withInvalidUsername_returns401() {
        String body = """
                {
                    "username": "nonexistentuser",
                    "password": "TestPass123!"
                }
                """;

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .post("/auth");

        response.then()
                .statusCode(401)
                .body("message", equalTo("Invalid username or password"));
    }

    @Test
    void authenticate_withInvalidPassword_returns401() {
        String body = """
                {
                    "username": "%s",
                    "password": "WrongPassword123!"
                }
                """.formatted(testUsername);

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .post("/auth");

        response.then()
                .statusCode(401)
                .body("message", equalTo("Invalid username or password"));
    }

    @Test
    void authenticate_withMissingUsername_returns400() {
        String body = """
                {
                    "password": "TestPass123!"
                }
                """;

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .post("/auth");

        response.then()
                .statusCode(400);
    }

    @Test
    void authenticate_withMissingPassword_returns400() {
        String body = """
                {
                    "username": "%s"
                }
                """.formatted(testUsername);

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .post("/auth");

        response.then()
                .statusCode(400);
    }

    @Test
    void authenticate_withBlankUsername_returns400() {
        String body = """
                {
                    "username": "",
                    "password": "TestPass123!"
                }
                """;

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .post("/auth");

        response.then()
                .statusCode(400);
    }

    @Test
    void authenticate_withBlankPassword_returns400() {
        String body = """
                {
                    "username": "%s",
                    "password": ""
                }
                """.formatted(testUsername);

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .post("/auth");

        response.then()
                .statusCode(400);
    }

    @Test
    void authenticate_withoutAuthorizationHeader_isAllowed() {
        // Verify that the /auth endpoint does not require authentication
        String body = """
                {
                    "username": "%s",
                    "password": "%s"
                }
                """.formatted(testUsername, testPassword);

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .post("/auth");

        // Should succeed without Authorization header
        response.then()
                .statusCode(200)
                .body("token", notNullValue());
    }

    @Test
    void authenticate_returnsValidJwtToken_thatCanBeUsed() {
        // First authenticate to get a token
        String authBody = """
                {
                    "username": "%s",
                    "password": "%s"
                }
                """.formatted(testUsername, testPassword);

        Response authResponse = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(authBody)
                .post("/auth");

        String token = authResponse.then()
                .statusCode(200)
                .extract()
                .path("token");

        // Verify the token works by calling a protected endpoint
        Response usersResponse = RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .get("/users");

        usersResponse.then().statusCode(200);
    }
}

