package com.valerehealth.brightree.model.brightree;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BrightreeReferralPatient {

    @JsonProperty("BrightreeID")
    private Integer brightreeID;

    @JsonProperty("PatientID")
    private String patientID;

    @JsonProperty("PatientFullName")
    private String patientFullName;

}
