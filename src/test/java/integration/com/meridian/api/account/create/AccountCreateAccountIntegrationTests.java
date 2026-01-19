package integration.com.meridian.api.account.create;

import integration.com.meridian.base.BaseTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;

public class AccountCreateAccountIntegrationTests extends BaseTest {

    private final String body =
                                """
                                {
                                	"accountType": "TRADING"
                                }
                                """;

    @Test
    void createAccountReturns201StatusCode() {

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + jwtToken)
                .body(body)
                .post("/users/1/accounts");

        response.then().statusCode(201).and()
                .body("accountType", equalTo("TRADING"));
    }

    @Test
    void createAccountForNonExistentUserReturns404StatusCode() {

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + jwtToken)
                .body(body)
                .post("/users/999/accounts");

        response.then().statusCode(404).and()
                .body("message", equalTo("No user found with id 999"));
    }

    @Test
    void createAccount_withoutAuthorizationHeader_returns401() {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .post("/users/1/accounts");

        response.then().statusCode(401);
    }

    @Test
    void createAccount_withInvalidToken_returns401() {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer invalid.token.here")
                .body(body)
                .post("/users/1/accounts");

        response.then().statusCode(401);
    }
}
