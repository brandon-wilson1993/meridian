package integration.com.meridian.api.users.delete;

import integration.com.meridian.base.BaseTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UsersDeleteUserIntegrationTests extends BaseTest {


    private String id;

    @BeforeEach
    void dataPopulation() {

        String body =
                """
                {
                      "firstName": "Delete",
                      "lastName": "Name"
                }
                """;

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + jwtToken)
                .body(body)
                .post("/users");

        id = response.then().extract().jsonPath().getString("id");
    }

    @Test
    void deleteUserReturns200StatusCode() {

        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + jwtToken)
                .delete("/users/" + id);

        response.then().statusCode(200);
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
}
