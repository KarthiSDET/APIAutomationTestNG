package com.spotify.oauth2.api;

import com.spotify.oauth2.utilis.ConfigLoader;
import io.restassured.response.Response;

import java.time.Instant;
import java.util.HashMap;

import static com.spotify.oauth2.api.RestResource.postAccount;


public class TokenManager {

    private static String accessToken;
    private static Instant expiryTime;

    public synchronized static String getToken(){
        try{
            if(accessToken == null || Instant.now().isAfter(expiryTime)) {
                System.out.println("Renewing token..");
                Response response = renewToken();
                accessToken = response.path("access_token");
                int expiryDurationInSeconds = response.path("expires_in");
                expiryTime = Instant.now().plusSeconds(expiryDurationInSeconds - 300);
            } else {
                System.out.println("Token is good to use");
            }
        }
        catch(Exception e){
            System.out.println("Error");
            e.printStackTrace();
            throw new RuntimeException("Abort!! Renew token failed");
        }
        return accessToken;
    }

    private static Response renewToken() {
        HashMap<String,String> formParams = new HashMap<>();
        formParams.put("grant_type", ConfigLoader.getInstance().getGrantType());
        formParams.put("client_id",ConfigLoader.getInstance().getClientId());
        formParams.put("client_secret",ConfigLoader.getInstance().getClientSecret());
        formParams.put("refresh_token",ConfigLoader.getInstance().getRefreshToken());

        Response response = postAccount(formParams);

        if(response.getStatusCode() != 200) {
            throw new RuntimeException("Abort!! Renew token failed");
        }
        return response;
    }
}
