package integration.com.meridian.api.cards.delete;

import integration.com.meridian.base.BaseTest;
import integration.com.meridian.data.TestDataServiceBody;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CardDeleteCardIntegrationTests extends BaseTest {

    private static Long creditAccountId;
    private static Long userId;
    private String cardId;

    @BeforeAll
    static void dataSetup() {

        testDataService.basicDataCreation();
        userId = testDataService.getUserId();
        creditAccountId = testDataService.getCreditAccountId();
    }

    @BeforeEach
    void createCard() {

        String body = String.format(TestDataServiceBody.CREATE_CARD_CREDIT.getBody(), creditAccountId);

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .post("/users/" + userId + "/cards");

        cardId = response.then().extract().path("id").toString();
    }

    @Test
    void deleteCardReturns200StatusCode() {

        Response response = RestAssured.given()
                .delete("/cards/" + cardId);

        response.then().statusCode(200);
    }
}
