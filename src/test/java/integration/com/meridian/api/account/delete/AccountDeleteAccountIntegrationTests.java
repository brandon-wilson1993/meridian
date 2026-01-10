package integration.com.meridian.api.account.delete;

import integration.com.meridian.base.BaseTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AccountDeleteAccountIntegrationTests extends BaseTest {

    private final String body =
            """
            {
                "accountType": "TRADING"
            }
            """;

    private String id;

    @BeforeEach
    void dataSetup() {

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .post("/users/1/accounts");

        id = response.then().extract().path("id").toString();
    }

    @Test
    void deleteAccountReturns200StatusCode() {

        Response response = RestAssured.given()
                .delete("/accounts/" + id);

        response.then().statusCode(200);
    }
}
