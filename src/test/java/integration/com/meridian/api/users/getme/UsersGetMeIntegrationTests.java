package integration.com.meridian.api.users.getme;

import integration.com.meridian.base.BaseTest;
import integration.com.meridian.helper.jwt.JwtTestHelper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;

public class UsersGetMeIntegrationTests extends BaseTest {

    @Test
    void getMe_returnsCurrentUserData_whenValidTokenProvided() {

        // Generate a token for a specific user
        String userToken = JwtTestHelper.generateTestToken("testuser");

        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + userToken)
                .get("/users/me");

        response.then().statusCode(200)
                .body("username", equalTo("testuser"));
    }

    @Test
    void getMe_returns401_whenNoTokenProvided() {

        Response response = RestAssured.given()
                .get("/users/me");

        response.then().statusCode(401);
    }

    @Test
    void getMe_returns401_whenInvalidTokenProvided() {

        Response response = RestAssured.given()
                .header("Authorization", "Bearer invalid.token.here")
                .get("/users/me");

        response.then().statusCode(401);
    }

    @Test
    void getMe_returns404_whenUserNotFoundForToken() {

        // Generate a token for a user that doesn't exist
        String nonExistentUserToken = JwtTestHelper.generateTestToken("nonexistentuser");

        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + nonExistentUserToken)
                .get("/users/me");

        response.then().statusCode(404)
                .body("message", equalTo("User with username nonexistentuser not found"));
    }
}
