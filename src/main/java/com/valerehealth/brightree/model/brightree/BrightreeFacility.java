package com.valerehealth.brightree.model.brightree;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class BrightreeFacility {

    // required
    private String name;

    // Value used to identify the Sending/Receiving Facility.
    // Must be unique across all Receiving Sending/Facilities for this Referral Source.
    // required
    private String externalID;

    public BrightreeFacility(String externalID, String name) {
        this.externalID = externalID;
        this.name = name;
    }

}
