package integration.com.meridian.api.cards.getcard;

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

public class CardGetCardIntegrationTests extends BaseTest {

    private static Long creditAccountId;
    private static Long userId;
    private static String cardId;

    @BeforeAll
    static void dataSetup() {

        testDataService.basicDataCreation();
        userId = testDataService.getUserId();
        creditAccountId = testDataService.getCreditAccountId();

        // Create a test card
        String body = String.format(TestDataServiceBody.CREATE_CARD_CREDIT.getBody(), creditAccountId);

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .post("/users/" + userId + "/cards");

        cardId = response.then().extract().path("id").toString();
    }

    @Test
    void getCardReturns200StatusCode() {

        Response response = RestAssuredHelpers.requestHelper("/cards/" + cardId, RequestType.GET);

        response.then().statusCode(200).and()
                .body("id", equalTo(Integer.parseInt(cardId)))
                .body("cardType", equalTo("CREDIT"))
                .body("accountId", equalTo(creditAccountId.intValue()))
                .body("maskedCardNumber", matchesPattern("\\*{12}\\d{4}"));
    }

    @Test
    void getCardForNonExistentCardReturns404StatusCode() {

        Response response = RestAssuredHelpers.requestHelper("/cards/9999", RequestType.GET);

        response.then().statusCode(404).and()
                .body("message", equalTo("No card found with id 9999"));
    }
}
