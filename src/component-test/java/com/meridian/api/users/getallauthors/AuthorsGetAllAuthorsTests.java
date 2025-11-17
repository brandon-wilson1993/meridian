package com.meridian.api.users.getallauthors;

import com.meridian.api.base.BaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;

public class AuthorsGetAllAuthorsTests extends BaseTest {

    @Test
    void getAllAuthorsReturns200StatusCode() {

        Response response = RestAssured.given()
                .get("/authors");

        response.then().statusCode(200).and()
                .body("_embedded.authorList.find() { it.id == 1 }.lastName", equalTo("Travis"))
                .body("_embedded.authorList.find() { it.id == 2 }.lastName", equalTo("Seuss"))
                .body("_embedded.authorList.find() { it.id == 3 }.lastName", equalTo("Rowling"));
    }
}
