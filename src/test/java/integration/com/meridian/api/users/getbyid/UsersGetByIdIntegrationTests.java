package integration.com.meridian.api.users.getbyid;

import integration.com.meridian.base.BaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;

public class UsersGetByIdIntegrationTests extends BaseTest {

    @Test
    void getByIdReturns200StatusCode() {

        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + jwtToken)
                .get("/users/1");

        response.then().statusCode(200).and()
                .body("id", equalTo(1))
                .body("firstName", equalTo("Karen"))
                .body("lastName", equalTo("Travis"));
    }

    @Test
    void getByIdReturns404StatusCodeWhenAuthorNotFound() {

        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + jwtToken)
                .get("/users/9999");

        response.then().statusCode(404).and()
                .body("message", equalTo("User with id 9999 not found"));
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
}
