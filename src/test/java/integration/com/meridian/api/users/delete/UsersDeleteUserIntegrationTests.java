package integration.com.meridian.api.users.delete;

import integration.com.meridian.api.base.BaseTest;
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

        Response response = RestAssured.given().contentType(ContentType.JSON).body(body).post("/users");

        id = response.then().extract().jsonPath().getString("id");
    }

    @Test
    void deleteUserReturns200StatusCode() {

        Response response = RestAssured.given().delete("/users/" + id);

        response.then().statusCode(200);
    }
}
