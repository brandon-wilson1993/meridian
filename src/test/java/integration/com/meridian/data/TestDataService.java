package integration.com.meridian.data;

import com.meridian.api.account.AccountType;
import integration.com.meridian.helper.jwt.JwtTestHelper;
import integration.com.meridian.helper.restassured.RequestType;
import integration.com.meridian.helper.restassured.RestAssuredHelpers;
import io.restassured.response.Response;

public class TestDataService {

    private Long checkingAccountId;
    private Long creditAccountId;
    private Long savingAccountId;
    private Long tradingAccountId;
    private Long userId;
    private String jwtToken;

    public TestDataService() {

        this.jwtToken = JwtTestHelper.generateTestToken();
    }

    public Long getCheckingAccountId() {
        return checkingAccountId;
    }

    public Long getCreditAccountId() {
        return creditAccountId;
    }

    public Long getSavingAccountId() {
        return savingAccountId;
    }

    public Long getTradingAccountId() {
        return tradingAccountId;
    }

    public Long getUserId() {
        return userId;
    }

    public void basicDataCreation(String username) {

        Response response = RestAssuredHelpers.requestHelper("/users", RequestType.POST, TestDataServiceBody.CREATE_USER.getBody(username), jwtToken);
        userId = response.then().extract().jsonPath().getLong("id");

        checkingAccountId = RestAssuredHelpers.requestHelper("/users/" + userId + "/accounts", RequestType.POST, TestDataServiceBody.CREATE_ACCOUNT_CHECKING.getBody(), jwtToken).then().extract().jsonPath().getLong("id");
        creditAccountId = RestAssuredHelpers.requestHelper("/users/" + userId + "/accounts", RequestType.POST, TestDataServiceBody.CREATE_ACCOUNT_CREDIT.getBody(), jwtToken).then().extract().jsonPath().getLong("id");
        savingAccountId = RestAssuredHelpers.requestHelper("/users/" + userId + "/accounts", RequestType.POST, TestDataServiceBody.CREATE_ACCOUNT_SAVINGS.getBody(), jwtToken).then().extract().jsonPath().getLong("id");
        tradingAccountId = RestAssuredHelpers.requestHelper("/users/" + userId + "/accounts", RequestType.POST, TestDataServiceBody.CREATE_ACCOUNT_TRADING.getBody(), jwtToken).then().extract().jsonPath().getLong("id");
    }
}
