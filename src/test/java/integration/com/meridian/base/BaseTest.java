package integration.com.meridian.base;

import integration.com.meridian.data.TestDataService;
import integration.com.meridian.helper.jwt.JwtTestHelper;
import io.restassured.RestAssured;

import org.junit.jupiter.api.BeforeAll;

public class BaseTest {

    protected static TestDataService testDataService;
    protected static String jwtToken;

    @BeforeAll
    static void setup() {

        RestAssured.baseURI = "http://localhost:8080";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        testDataService = new TestDataService();
        jwtToken = JwtTestHelper.generateTestToken();
    }
}
