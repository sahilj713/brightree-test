package com.valerehealth.brightree.model.brightree;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import lombok.Data;

@Data
public class BrightreeAuthRequest {

    private String grantType = "client_credentials";
    private String clientId;
    private String clientSecret;

    public MultiValueMap<String, String> toMultiValueMap() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        map.add("grant_type", getGrantType());
        map.add("client_id", getClientId());
        map.add("client_secret", getClientSecret());

        return map;
    }

}
