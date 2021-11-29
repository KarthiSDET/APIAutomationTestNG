package com.spotify.oauth2.api.applicationApi;

import com.spotify.oauth2.api.RestResource;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.utilis.ConfigLoader;
import io.restassured.response.Response;

import static com.spotify.oauth2.api.Route.PLAYLISTS;
import static com.spotify.oauth2.api.Route.USERS;

import static com.spotify.oauth2.api.TokenManager.getToken;

public class PlaylistApi {

    public static Response post(Playlist requestPlaylist) {
        return RestResource.post(getToken(),USERS + "/" + ConfigLoader.getInstance().getUserId() + PLAYLISTS,requestPlaylist);
    }

    public static Response post(Playlist requestPlaylist, String accessToken) {
        return RestResource.post(accessToken,USERS + "/" + ConfigLoader.getInstance().getUserId() + PLAYLISTS,requestPlaylist);
    }

    public static Response get(String playlisId) {
        return RestResource.get(getToken(),PLAYLISTS + "/" + playlisId);
    }

    public static Response update(String playlistId, Playlist requestPlaylist) {
        return RestResource.put(getToken(),PLAYLISTS + "/" + playlistId, requestPlaylist);
    }
}
