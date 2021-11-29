package com.spotify.oauth2.api;

import io.restassured.response.Response;

import java.util.HashMap;

import static com.spotify.oauth2.api.Route.API;
import static com.spotify.oauth2.api.Route.TOKEN;
import static com.spotify.oauth2.api.SpecBuilder.*;
import static io.restassured.RestAssured.given;

public class RestResource {

    public static Response post(String token, String path, Object requestPlaylist) {
        return given(getRequestSpec()).
            auth().oauth2(token)
            .body(requestPlaylist).when()
            .post(path).then().spec(getResponseSpec())
            .extract().response();
    }

    public static Response get(String token,String path) {
        return given(getRequestSpec())
        .auth().oauth2(token)
            .when().get(path).then()
            .spec(getResponseSpec()).extract().response();
    }

    public static Response put(String token,String path,Object requestPlaylist) {
        return given(getRequestSpec())
            .auth().oauth2(token).body(requestPlaylist).when().put(path)
            .then().spec(getResponseSpec()).extract().response();
    }

    public static Response postAccount(HashMap<String,String> formParams){
        return given(getAccountRequestSpec())
            .formParams(formParams)
            .when().post(API + TOKEN)
            .then().spec(getResponseSpec())
            .extract().response();
    }
}
