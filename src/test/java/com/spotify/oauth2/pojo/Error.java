
package com.spotify.oauth2.pojo;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@Value
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Error {

    @JsonProperty("error")
    InnerError innerError;
}
