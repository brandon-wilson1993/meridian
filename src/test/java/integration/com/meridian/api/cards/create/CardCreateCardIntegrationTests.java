package integration.com.meridian.api.cards.create;

import integration.com.meridian.base.BaseTest;
import integration.com.meridian.data.TestDataServiceBody;
import integration.com.meridian.helper.restassured.RequestType;
import integration.com.meridian.helper.restassured.RestAssuredHelpers;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;

public class CardCreateCardIntegrationTests extends BaseTest {

    private static Long creditAccountId;
    private static Long debitAccountId;
    private static Long userId;

    @BeforeAll
    static void dataSetup() {

        testDataService.basicDataCreation();
        userId = testDataService.getUserId();
        creditAccountId = testDataService.getCreditAccountId();
        debitAccountId = testDataService.getDebitAccountId();
    }

    @Test
    void createCreditCardReturns201StatusCode() {

        String body = String.format(TestDataServiceBody.CREATE_CARD_CREDIT.getBody(), creditAccountId);

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .post("/users/" + userId + "/cards");

        response.then().statusCode(201).and()
                .body("cardType", equalTo("CREDIT"))
                .body("accountId", equalTo(creditAccountId.intValue()))
                .body("maskedCardNumber", matchesPattern("\\*{12}\\d{4}"));
    }

    @Test
    void createDebitCardReturns201StatusCode() {

        String body = String.format(TestDataServiceBody.CREATE_CARD_DEBIT.getBody(), debitAccountId);

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .post("/users/" + userId + "/cards");

        response.then().statusCode(201).and()
                .body("cardType", equalTo("DEBIT"))
                .body("accountId", equalTo(debitAccountId.intValue()))
                .body("maskedCardNumber", matchesPattern("\\*{12}\\d{4}"));
    }

    @Test
    void createCardForNonExistentUserReturns404StatusCode() {

        String body = String.format(TestDataServiceBody.CREATE_CARD_CREDIT.getBody(), creditAccountId);

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .post("/users/999/cards");

        response.then().statusCode(404).and()
                .body("message", equalTo("No user found with id 999"));
    }

    @Test
    void createCardForNonExistentAccountReturns404StatusCode() {

        String body = String.format(TestDataServiceBody.CREATE_CARD_CREDIT.getBody(), 999);

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .post("/users/" + userId + "/cards");

        response.then().statusCode(404).and()
                .body("message", equalTo("No account found with id 999"));
    }

    @Test
    void createCreditCardWithDebitAccountReturns400StatusCode() {

        String body = String.format(TestDataServiceBody.CREATE_CARD_CREDIT.getBody(), debitAccountId);

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .post("/users/" + userId + "/cards");

        response.then().statusCode(400).and()
                .body("message", equalTo("Card type CREDIT does not match account type DEBIT"));
    }

    @Test
    void createDebitCardWithCreditAccountReturns400StatusCode() {

        String body = String.format(TestDataServiceBody.CREATE_CARD_DEBIT.getBody(), creditAccountId);

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .post("/users/" + userId + "/cards");

        response.then().statusCode(400).and()
                .body("message", equalTo("Card type DEBIT does not match account type CREDIT"));
    }
}
