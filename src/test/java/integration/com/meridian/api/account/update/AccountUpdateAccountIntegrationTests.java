package integration.com.meridian.api.account.update;

import integration.com.meridian.base.BaseTest;
import integration.com.meridian.helper.restassured.RequestType;
import integration.com.meridian.helper.restassured.RestAssuredHelpers;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;

public class AccountUpdateAccountIntegrationTests extends BaseTest {

    private String body = """
            {
            	"accountType": "TRADING"
            }
            """;

    @BeforeAll
    static void dataSetup() {

        testDataService.basicDataCreation();
    }

    @Test
    void updateAccountReturns200StatusCode() {

        Response response = RestAssuredHelpers.requestHelper("/accounts/" + testDataService.getCheckingAccountId(), RequestType.PUT, body, jwtToken);

        response.then().statusCode(200).and()
                .body("id", equalTo(testDataService.getCheckingAccountId().intValue()))
                .body("accountType", equalTo("TRADING"));
    }

    @Test
    void updateAccountReturns404StatusCodeInvalidAccountId() {

        Response response = RestAssuredHelpers.requestHelper("/accounts/9999", RequestType.PUT, body, jwtToken);

        response.then().statusCode(404).and()
                .body("message", equalTo("Account with id 9999 not found"));
    }

    @Test
    void updateAccount_withoutAuthorizationHeader_returns401() {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .put("/accounts/1");

        response.then().statusCode(401);
    }

    @Test
    void updateAccount_withInvalidToken_returns401() {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer invalid.token.here")
                .body(body)
                .put("/accounts/1");

        response.then().statusCode(401);
    }
}
