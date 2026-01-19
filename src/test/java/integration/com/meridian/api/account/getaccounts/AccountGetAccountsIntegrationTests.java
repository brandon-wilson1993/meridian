package integration.com.meridian.api.account.getaccounts;

import integration.com.meridian.base.BaseTest;
import integration.com.meridian.helper.restassured.RequestType;
import integration.com.meridian.helper.restassured.RestAssuredHelpers;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;

public class AccountGetAccountsIntegrationTests extends BaseTest {

    @BeforeAll
    static void dataSetup() {

        testDataService.basicDataCreation();
    }

    @Test
    void getAccountsReturns200StatusCode() {

        Response response = RestAssuredHelpers.requestHelper("/users/" + testDataService.getUserId() + "/accounts", RequestType.GET, "", jwtToken);

        response.then().statusCode(200).and()
                .body(String.format("find() { it.id == %s }.accountType", testDataService.getCheckingAccountId()), equalTo("CHECKING"))
                .body(String.format("find() { it.id == %s }.accountType", testDataService.getSavingAccountId()), equalTo("SAVINGS"))
                .body(String.format("find() { it.id == %s }.accountType", testDataService.getTradingAccountId()), equalTo("TRADING"));
    }

    @Test
    void getAccountsReturns200StatusCodeInvalidUserId() {

        Response response = RestAssuredHelpers.requestHelper("/users/9999/accounts", RequestType.GET, "", jwtToken);

        response.then().statusCode(404).and()
                .body("message", equalTo("No user found with id 9999"));
    }

    @Test
    void getAllAccounts_withoutAuthorizationHeader_returns401() {
        Response response = RestAssured.given()
                .get("/users/1/accounts");

        response.then().statusCode(401);
    }

    @Test
    void getAllAccounts_withInvalidToken_returns401() {
        Response response = RestAssured.given()
                .header("Authorization", "Bearer invalid.token.here")
                .get("/users/1/accounts");

        response.then().statusCode(401);
    }
}
