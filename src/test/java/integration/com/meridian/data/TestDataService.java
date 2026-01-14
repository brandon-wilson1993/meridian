package integration.com.meridian.data;

import com.meridian.api.account.AccountType;
import integration.com.meridian.helper.restassured.RequestType;
import integration.com.meridian.helper.restassured.RestAssuredHelpers;

public class TestDataService {

    private Long checkingAccountId;
    private Long creditAccountId;
    private Long debitAccountId;
    private Long savingAccountId;
    private Long tradingAccountId;
    private Long userId;

    public TestDataService() {

        System.out.println("Test Data Service constructor");
    }

    public Long getCheckingAccountId() {
        return checkingAccountId;
    }

    public Long getCreditAccountId() {
        return creditAccountId;
    }

    public Long getDebitAccountId() {
        return debitAccountId;
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

    public void basicDataCreation() {

        userId = RestAssuredHelpers.requestHelper("/users", RequestType.POST, TestDataServiceBody.CREATE_USER.getBody()).then().extract().jsonPath().getLong("id");

        checkingAccountId = RestAssuredHelpers.requestHelper("/users/" + userId + "/accounts", RequestType.POST, TestDataServiceBody.CREATE_ACCOUNT_CHECKING.getBody()).then().extract().jsonPath().getLong("id");
        creditAccountId = RestAssuredHelpers.requestHelper("/users/" + userId + "/accounts", RequestType.POST, TestDataServiceBody.CREATE_ACCOUNT_CREDIT.getBody()).then().extract().jsonPath().getLong("id");
        debitAccountId = RestAssuredHelpers.requestHelper("/users/" + userId + "/accounts", RequestType.POST, TestDataServiceBody.CREATE_ACCOUNT_DEBIT.getBody()).then().extract().jsonPath().getLong("id");
        savingAccountId = RestAssuredHelpers.requestHelper("/users/" + userId + "/accounts", RequestType.POST, TestDataServiceBody.CREATE_ACCOUNT_SAVINGS.getBody()).then().extract().jsonPath().getLong("id");
        tradingAccountId = RestAssuredHelpers.requestHelper("/users/" + userId + "/accounts", RequestType.POST, TestDataServiceBody.CREATE_ACCOUNT_TRADING.getBody()).then().extract().jsonPath().getLong("id");
    }
}
