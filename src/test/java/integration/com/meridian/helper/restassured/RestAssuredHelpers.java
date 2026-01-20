package integration.com.meridian.helper.restassured;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestAssuredHelpers {

    public static Response requestHelper(String uri, String jwtToken, RequestType requestType) {

        return requestHelperInternal(uri, requestType, "", jwtToken);
    }

    public static Response requestHelper(String uri, RequestType requestType, String body) {

        return requestHelperInternal(uri, requestType, body, null);
    }

    public static Response requestHelper(String uri, RequestType requestType, String body, String jwtToken) {

        return requestHelperInternal(uri, requestType, body, jwtToken);
    }

    private static Response requestHelperInternal(String uri, RequestType requestType, String body, String jwtToken) {

        RequestSpecification requestSpecification = RestAssured.given().contentType(ContentType.JSON);

        if (jwtToken != null && !jwtToken.isEmpty()) {
            requestSpecification.header("Authorization", "Bearer " + jwtToken);
        }

        if (!body.isEmpty()) {

            requestSpecification.body(body);
        }

        Response response = null;

        switch (requestType) {
            case DELETE -> {
                response = requestSpecification.delete(uri);
            }
            case GET -> {
                response = requestSpecification.get(uri);
            }
            case POST -> {
                response = requestSpecification.post(uri);
            }
            case PUT -> {
                response = requestSpecification.put(uri);
            }
        }

        return response;
    }
}
