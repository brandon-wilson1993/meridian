package integration.com.meridian.api.account.getaccounts;

import integration.com.meridian.base.BaseTest;
import integration.com.meridian.helper.restassured.RequestType;
import integration.com.meridian.helper.restassured.RestAssuredHelpers;
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

        Response response = RestAssuredHelpers.requestHelper("/users/" + testDataService.getUserId() + "/accounts", RequestType.GET);

        response.then().statusCode(200).and()
                .body(String.format("find() { it.id == %s }.accountType", testDataService.getCheckingAccountId()), equalTo("CHECKING"))
                .body(String.format("find() { it.id == %s }.accountType", testDataService.getSavingAccountId()), equalTo("SAVINGS"))
                .body(String.format("find() { it.id == %s }.accountType", testDataService.getTradingAccountId()), equalTo("TRADING"));
    }
}
