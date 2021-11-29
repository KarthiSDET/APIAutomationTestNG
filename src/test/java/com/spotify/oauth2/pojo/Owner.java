
package com.spotify.oauth2.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@Value
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Owner {

    @JsonProperty("display_name")
     String displayName;
    @JsonProperty("external_urls")
     ExternalUrls__1 externalUrls;
    @JsonProperty("href")
     String href;
    @JsonProperty("id")
     String id;
    @JsonProperty("type")
     String type;
    @JsonProperty("uri")
     String uri;
}
