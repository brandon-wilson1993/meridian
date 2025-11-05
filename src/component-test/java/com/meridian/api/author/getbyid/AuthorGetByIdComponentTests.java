package com.meridian.api.author.getbyid;

import com.meridian.api.base.BaseTest;
import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;

public class AuthorGetByIdComponentTests extends BaseTest {

    @Test
    void getByIdReturns200StatusCode() {

        Response response = RestAssured.given().log().ifValidationFails()
                .get("/authors/1");

        response.then().statusCode(200).and()
                .body("id", equalTo(1))
                .body("firstName", equalTo("Karen"))
                .body("lastName", equalTo("Travis"));
    }

    @Test
    void getByIdReturns404StatusCodeWhenAuthorNotFound() {

        Response response = RestAssured.given().log().ifValidationFails()
                .get("/authors/9999");

        response.prettyPrint();
        response.then().statusCode(404);
    }
}
