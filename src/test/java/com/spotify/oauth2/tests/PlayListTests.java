package com.spotify.oauth2.tests;

import com.spotify.oauth2.api.StatusCode;
import com.spotify.oauth2.api.applicationApi.PlaylistApi;
import com.spotify.oauth2.pojo.Error;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.utilis.DataLoader;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.spotify.oauth2.utilis.FakerUtils.generateDescription;
import static com.spotify.oauth2.utilis.FakerUtils.generateName;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Epic("Spotify Oauth2.0")
@Feature("Playlist API")
public class PlayListTests extends BaseTest{

    @Story("Create a playlist story")
    @Link("https://example.org")
    @Issue("123")
    @TmsLink("test-1")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Create music playlist in spotify with top 10 songs")
    @Test(description = "Create playlist")
    public void shouldBeAbleToCreatePlayList() {
        Playlist requestPlaylist =
            playlistBuilder(generateName(), generateDescription(), false);
        Response response = PlaylistApi.post(requestPlaylist);
        assertStatusCode(response.statusCode(), StatusCode.CODE_201);
        assertPlaylistEqual(requestPlaylist, response.as(Playlist.class));
        //        Playlist responsePlaylist = given(getRequestSpec())
        //            .body(requestPlaylist)
        //            .when().post("/users/f3w6ftjjencx9qau4wfv1getn/playlists")
        //            .then().spec(getResponseSpec())
        //            .assertThat()
        //            .statusCode(201)
        //            .extract().as(Playlist.class);
        //        assertThat(responsePlaylist.getName(),equalTo(requestPlaylist.getName()));
        //        assertThat(responsePlaylist.getDescription(),equalTo(requestPlaylist.getDescription()));
        //        assertThat(responsePlaylist.getPublic(),equalTo(requestPlaylist.getPublic()));
    }

    @Description("Get yuvan music playlist in spotify")
    @Story("Get a playlist story")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Get playlist")
    public void shouldBeAbleToGetAPlayList() {

        Playlist requestPlaylist =
            playlistBuilder(generateName(), generateDescription(), false);
        Response response = PlaylistApi.get(DataLoader.getInstance().getPlayListId());
        assertStatusCode(response.statusCode(), StatusCode.CODE_200);
        assertPlaylistEqual(requestPlaylist, response.as(Playlist.class));
        //        given(getRequestSpec())
        //            .when().get("playlists/2v1EopVbndg6bB0Gx4gzYu")
        //            .then().spec(getResponseSpec())
        //            .assertThat()
        //            .statusCode(200)
        //            .body("name",equalTo("New Playlist"))
        //            .log().all();
    }


    @Description("Update music playlist in spotify with new trending song")
    @Story("Update a playlist story")
    @Severity(SeverityLevel.MINOR)
    @Test(description = "Update playlist")
    public void shouldBeAbleToUpdateAPlayList() {

        Playlist requestPlaylist =
            playlistBuilder(generateName(), generateDescription(), false);
        Response response =
            PlaylistApi.update(DataLoader.getInstance().getUpdatePlayListId(), requestPlaylist);
        assertStatusCode(response.statusCode(), StatusCode.CODE_200);
    }

    @Description("Create music playlist in spotify without song name")
    @Story("Create a playlist story")
    @Severity(SeverityLevel.MINOR)
    @Test(description = "Create playlist without name")
    public void shouldBeAbleToCreatePlayListWithoutName() {

        Playlist requestPlaylist = Playlist.builder().description(generateDescription())._public(false).build();
        Response response = PlaylistApi.post(requestPlaylist);
        assertError(response.as(Error.class), StatusCode.CODE_400, StatusCode.CODE_400.msg);
    }

    @Description("Create music playlist in spotify with expired token")
    @Story("Create a playlist story")
    @Severity(SeverityLevel.BLOCKER)
    @Test(description = "Create playlist with expired token")
    public void shouldNotBeAbleToCreatePlayListWithExpiredToken() {

        String accessToken = "1234";
        Playlist requestPlaylist =
            playlistBuilder(generateName(), generateDescription(), false);
        Response response = PlaylistApi.post(requestPlaylist, accessToken);
        assertError(response.as(Error.class), StatusCode.CODE_401, StatusCode.CODE_401.msg);
    }

    @Step
    public Playlist playlistBuilder(String name, String description, boolean _public) {
        return Playlist.builder().name(name).description(description)._public(_public).build();
    }

    @Step
    public void assertPlaylistEqual(Playlist requestPlaylist, Playlist responsePlaylist) {
//        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
//        assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.get_public(), equalTo(requestPlaylist.get_public()));
    }

    @Step
    public void assertStatusCode(int actualStatusCode, StatusCode statusCode) {
        assertThat(actualStatusCode, equalTo(statusCode.code));
    }

    @Step
    public void assertError(Error responseError, StatusCode statusCode,
        String expectedErrorMessage) {
        assertThat(responseError.getInnerError().getStatus(), equalTo(statusCode.code));
        assertThat(responseError.getInnerError().getMessage(), equalTo(statusCode.msg));
    }
}
