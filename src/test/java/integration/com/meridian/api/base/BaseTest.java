package integration.com.meridian.api.base;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {

    @BeforeAll
    static void setup() {

        RestAssured.baseURI = "http://meridian:8080";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}
