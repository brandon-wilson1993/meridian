package integration.com.meridian.api.users.update;

import integration.com.meridian.base.BaseTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;

public class UsersUpdateUserIntegrationTests extends BaseTest {

    private String id;

    @BeforeEach
    void dataPopulation() {

        String body =
                """
                {
                      "firstName": "Update",
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
    void updateUserReturns200StatusCode() {

        String body =
                """
                {
                      "firstName": "Updated",
                      "lastName": "Name2"
                }
                """;

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + jwtToken)
                .body(body)
                .put("/users/" + id);

        response.then().statusCode(200).and()
                .body("id", equalTo(Integer.parseInt(id)))
                .body("firstName", equalTo("Updated"))
                .body("lastName", equalTo("Name2"));
    }

    @Test
    void updateUser_withoutAuthorizationHeader_returns401() {
        String body =
                """
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
        String body =
                """
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
}
