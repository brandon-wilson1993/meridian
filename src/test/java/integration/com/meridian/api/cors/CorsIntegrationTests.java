package integration.com.meridian.api.cors;

import integration.com.meridian.base.BaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

public class CorsIntegrationTests extends BaseTest {

    @Test
    void optionsRequest_withAllowedOrigin_returnsCorsHeaders() {
        Response response = RestAssured.given()
                .header("Origin", "http://localhost:8000")
                .header("Access-Control-Request-Method", "POST")
                .header("Access-Control-Request-Headers", "Authorization,Content-Type")
                .options("/auth");

        response.then()
                .statusCode(200)
                .header("Access-Control-Allow-Origin", "http://localhost:8000")
                .header("Access-Control-Allow-Methods", containsString("POST"))
                .header("Access-Control-Allow-Headers", notNullValue())
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Max-Age", "3600");
    }
}
