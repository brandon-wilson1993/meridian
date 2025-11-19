package integration.com.meridian.api.users.create;

import integration.com.meridian.api.base.BaseTest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;

public class UsersCreateUserIntegrationTests extends BaseTest {

    private final String body =
                                """
                                {
                                	"firstName": "Testing",
                                	"lastName": "Name"
                                }
                                """;

    @Test
    void createUserReturns201StatusCode() {

        Response response = RestAssured.given().contentType(ContentType.JSON).body(body).post("/users");

        response.then().statusCode(201).and()
                .body("firstName", equalTo("Testing"))
                .body("lastName", equalTo("Name"));
    }
}
