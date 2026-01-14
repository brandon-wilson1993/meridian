package integration.com.meridian.api.cards.getcards;

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
import static org.hamcrest.Matchers.hasSize;

public class CardGetCardsIntegrationTests extends BaseTest {

    private static Long creditAccountId;
    private static Long debitAccountId;
    private static Long userId;

    @BeforeAll
    static void dataSetup() {

        testDataService.basicDataCreation();
        userId = testDataService.getUserId();
        creditAccountId = testDataService.getCreditAccountId();
        debitAccountId = testDataService.getDebitAccountId();

        // Create test cards
        String creditCardBody = String.format(TestDataServiceBody.CREATE_CARD_CREDIT.getBody(), creditAccountId);
        String debitCardBody = String.format(TestDataServiceBody.CREATE_CARD_DEBIT.getBody(), debitAccountId);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(creditCardBody)
                .post("/users/" + userId + "/cards");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(debitCardBody)
                .post("/users/" + userId + "/cards");
    }

    @Test
    void getCardsReturns200StatusCode() {

        Response response = RestAssuredHelpers.requestHelper("/users/" + userId + "/cards", RequestType.GET);

        response.then().statusCode(200).and()
                .body("$", hasSize(2))
                .body("find { it.cardType == 'CREDIT' }.accountId", equalTo(creditAccountId.intValue()))
                .body("find { it.cardType == 'DEBIT' }.accountId", equalTo(debitAccountId.intValue()));
    }

    @Test
    void getCardsForNonExistentUserReturns404StatusCode() {

        Response response = RestAssuredHelpers.requestHelper("/users/9999/cards", RequestType.GET);

        response.then().statusCode(404).and()
                .body("message", equalTo("No user found with id 9999"));
    }
}
