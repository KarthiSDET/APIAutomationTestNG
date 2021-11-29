package com.rest;

import io.restassured.RestAssured;
import io.restassured.authentication.FormAuthConfig;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.session.SessionFilter;
import io.restassured.http.Cookie;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class FormAuthentication {

    @BeforeClass
    public void beforeClass() {
        RequestSpecBuilder requestSpecBuilder =
            new RequestSpecBuilder().setRelaxedHTTPSValidation().setBaseUri("https://localhost:8443/");
        RestAssured.requestSpecification =
            requestSpecBuilder.build();
    }

    @Test
    public void form_authentication_using_csrf_token() {
        SessionFilter filter = new SessionFilter();
        given().
            auth()
            .form("John", "Doe", new FormAuthConfig("/signin", "txtUsername", "txtPassword").withAutoDetectionOfCsrf()).
            filter(filter).
            log().all().
        when()
            .get("/login").
        then().
            log().all().
            assertThat().
            statusCode(200);

        System.out.println("Session id = " + filter.getSessionId());

        given().
            log().all()
            .sessionId(filter.getSessionId())
        .when()
            .get("/profile/index")
        .then()
            .log().all()
            .assertThat()
            .statusCode(200);

    }

    @Test
    public void form_authentication_using_csrf_token_cookie_example() {
        SessionFilter filter = new SessionFilter();
        given().
            auth()
            .form("John", "Doe", new FormAuthConfig("/signin", "txtUsername", "txtPassword").withAutoDetectionOfCsrf()).
            filter(filter).
            log().all().
            when()
            .get("/login").
            then().
            log().all().
            assertThat().
            statusCode(200);

        System.out.println("Session id = " + filter.getSessionId());

        Cookie cookie = new Cookie.Builder("JSESSIONID", filter.getSessionId()).setSecured(true)
            .setHttpOnly(true).setComment("My cookie").build();

        given().
            cookie(cookie).
            log().all()
            .when()
            .get("/profile/index")
            .then()
            .log().all()
            .assertThat()
            .statusCode(200);

    }
}
