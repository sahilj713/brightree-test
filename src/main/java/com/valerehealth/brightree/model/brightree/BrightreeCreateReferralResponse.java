package com.valerehealth.brightree.model.brightree;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BrightreeCreateReferralResponse {

    @JsonProperty("Messages")
    private List<String> messages;

    @JsonProperty("ReferralKey")
    private Integer referralKey;

}
