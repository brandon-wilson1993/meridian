package integration.com.meridian.api.users.create;

import integration.com.meridian.base.BaseTest;
import integration.com.meridian.helper.jwt.JwtTokenHelper;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.hamcrest.Matchers.equalTo;

public class UsersCreateUserIntegrationTests extends BaseTest {

    private final String body =
                                """
                                {
                                	"firstName": "Testing",
                                	"lastName": "Name",
                                	"username": "%s",
                                	"password": "TestPass123!"
                                }
                                """.formatted(RandomStringUtils.randomAlphabetic(8));

    @Test
    void createUserReturns201StatusCode() {

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + jwtToken)
                .body(body)
                .post("/users");

        response.then().statusCode(201).and()
                .body("firstName", equalTo("Testing"))
                .body("lastName", equalTo("Name"));
    }

    @Test
    void createUser_withoutAuthorizationHeader_returns401() {

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .post("/users");

        response.then().statusCode(401);
    }

    @Test
    void createUser_withInvalidToken_returns401() {

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer invalid.token.here")
                .body(body)
                .post("/users");

        response.then().statusCode(401);
    }

    @Test
    void createUser_withExpiredToken_returns401() {

        String expiredToken = JwtTokenHelper.generateExpiredToken();

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + expiredToken)
                .body(body)
                .post("/users");

        response.then().statusCode(401);
    }

    @Test
    void createUser_withInvalidPassword_returns400() {

        String invalidPasswordBody = """
                {
                    "firstName": "Testing",
                    "lastName": "Name",
                    "username": "%s",
                    "password": "short"
                }
                """.formatted(RandomStringUtils.randomAlphabetic(8));

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + jwtToken)
                .body(invalidPasswordBody)
                .post("/users");

        response.then().statusCode(400)
                .body("error", equalTo("Password must be at least 8 characters and contain at least one lowercase letter, one uppercase letter, and one special character"));
    }

    @Test
    void createUser_withPasswordMissingUppercase_returns400() {

        String invalidPasswordBody = """
                {
                    "firstName": "Testing",
                    "lastName": "Name",
                    "username": "%s",
                    "password": "testpass123!"
                }
                """.formatted(RandomStringUtils.randomAlphabetic(8));

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + jwtToken)
                .body(invalidPasswordBody)
                .post("/users");

        response.then().statusCode(400)
                .body("error", equalTo("Password must be at least 8 characters and contain at least one lowercase letter, one uppercase letter, and one special character"));
    }

    @Test
    void createUser_withPasswordMissingSpecialChar_returns400() {

        String invalidPasswordBody = """
                {
                    "firstName": "Testing",
                    "lastName": "Name",
                    "username": "%s",
                    "password": "TestPass123"
                }
                """.formatted(RandomStringUtils.randomAlphabetic(8));

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + jwtToken)
                .body(invalidPasswordBody)
                .post("/users");

        response.then().statusCode(400)
                .body("error", equalTo("Password must be at least 8 characters and contain at least one lowercase letter, one uppercase letter, and one special character"));
    }
}
