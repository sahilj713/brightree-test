package com.valerehealth.brightree.model.brightree;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BrightreeReferralSalesOrder {

    @JsonProperty("SalesOrderKey")
    private String salesOrderKey;

    @JsonProperty("SalesOrderStatus")
    private String salesOrderStatus;

    @JsonProperty("Tracking")
    private List<BrightreeReferralTracking> tracking;

}
