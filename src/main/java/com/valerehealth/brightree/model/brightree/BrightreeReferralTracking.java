package com.valerehealth.brightree.model.brightree;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BrightreeReferralTracking {

    @JsonProperty("TrackingNumber")
    private String trackingNumber;

    @JsonProperty("ShipDate")
    private LocalDateTime shipDate;

    @JsonProperty("Carrier")
    private BrightreeShipmentCarrier carrier;

    // Lookup Values for Carrier
    // ID Value Notes
    // 2 UPS United Parcel Service
    // 3 FEDEX Federal Express
    // 4 USPS United States Postal Service
    // 5 Other Other
    // 6 Spee-Dee Spee-Dee
    // 7 On-Trac On-Trac
    // 8 UDS United Delivery Service
    // 9 PD Priority Dispatch
    // 10 Dicom Dicom
    // 11 GSO Golden State Overnight
    // 12 Lonestar Lonestar
    // 13 Senvoy Senvoy
    // 14 LaserShip LaserShip

}
