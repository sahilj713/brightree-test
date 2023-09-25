package com.valerehealth.brightree.model.brightree;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BrightreeShipmentCarrier {

    @JsonProperty("ID")
    private String id;

    @JsonProperty("Value")
    private String value;

    @JsonProperty("Notes")
    private List<String> notes;

}
