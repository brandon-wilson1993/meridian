package integration.com.meridian.api.users.getall;

import integration.com.meridian.api.base.BaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;

public class UsersGetAllUsersIntegrationTests extends BaseTest {

    @Test
    void getAllAuthorsReturns200StatusCode() {

        Response response = RestAssured.given()
                .get("/users");

        response.then().statusCode(200).and()
                .body("_embedded.usersList.find() { it.id == 1 }.lastName", equalTo("Travis"))
                .body("_embedded.usersList.find() { it.id == 2 }.lastName", equalTo("Seuss"))
                .body("_embedded.usersList.find() { it.id == 3 }.lastName", equalTo("Rowling"));
    }
}
