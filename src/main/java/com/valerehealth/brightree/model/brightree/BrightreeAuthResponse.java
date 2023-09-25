package com.valerehealth.brightree.model.brightree;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BrightreeAuthResponse {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("expires_in")
    private Integer expiresIn;

}
