package integration.com.meridian.api.users.getall;

import integration.com.meridian.base.BaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;

public class UsersGetAllUsersIntegrationTests extends BaseTest {

    @Test
    void getAllAuthorsReturns200StatusCode() {

        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + jwtToken)
                .get("/users");

        response.then().statusCode(200).and()
                .body("find() { it.id == 1 }.lastName", equalTo("Travis"))
                .body("find() { it.id == 2 }.lastName", equalTo("Seuss"))
                .body("find() { it.id == 3 }.lastName", equalTo("Rowling"));
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
}
