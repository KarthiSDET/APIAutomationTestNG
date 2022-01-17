package com.spotify.oauth2.tests;

import com.spotify.oauth2.pojo.Owner;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;

import static io.restassured.RestAssured.given;

public class TestSample {

    public void postApi() {
        String pathParam = "value1";
        Response response = given().baseUri("https://wms.com").pathParam("v1", pathParam)
            .header("Content-type", ContentType.JSON).when().post("/login").then().extract().response();

        Assert.assertTrue(response.getStatusCode() == 200,"Status code is not 200");
        Assert.assertTrue(response.getStatusLine() == "Status is 200","Status message is not as expected");
        Assert.assertTrue(response.getBody().equals(Owner.class),"Response results doesn't match");

    }
}
