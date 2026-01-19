package integration.com.meridian.api.account.update;

import integration.com.meridian.base.BaseTest;
import integration.com.meridian.helper.restassured.RequestType;
import integration.com.meridian.helper.restassured.RestAssuredHelpers;
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
}
