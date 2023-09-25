package com.valerehealth.brightree.model.brightree;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BrightreeReferralStatus {

    // New, In Progress, Rejected, Accepted, Scheduled, Delivered, Cancelled, Picked Up, Receiving
    @JsonProperty("ReferralStatus")
    private String referralStatus;

    @JsonProperty("RejectionReason")
    private String rejectionReason;

    @JsonProperty("ReferralSalesOrder")
    private BrightreeReferralSalesOrder referralSalesOrder;

    @JsonProperty("ReferralPatient")
    private BrightreeReferralPatient referralPatient;

    @JsonProperty("SleepTherapies")
    private Object sleepTherapies;

}
