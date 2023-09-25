package com.valerehealth.brightree.model.valere;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ValereOrder {

    @NotNull
    private UUID id;

    @NotNull
    private String originatorOrderId;

    @NotNull
    private String originatorId;

    @NotNull
    private ValerePatient patient;

    @NotNull
    private List<ValereProduct> products;

    @NotNull
    private ValereFacility originatorFacility;

    @NotNull
    private ValereFacility integratorFacility;

    @NotNull
    private Instant createdAt;

    private ValerePrescriber prescriber;
    private List<ValereDocument> documents;
    private List<String> hcpcs;
    private List<String> diagnosisCodes;
    private String status;
    private List<String> notes;
    private List<ValerePayer> payers;

    // TODO: These might change later. For now, storing them on ValereOrder so we have a way to
    // capture the data.
    private Boolean pickupAllRentalItems;
    private String placeOfService;
    private Boolean printAmountOnDeliveryTicket;
    private Integer marketingRep;
    private Integer marketingReferral;
    private LocalDate requestedPickupDate;
    private LocalTime requestedPickupTime;
    private LocalDate requestedDeliveryDate;
    private LocalTime requestedDeliveryTime;
    private LocalDate actualDeliveryDate;
    private LocalTime actualDeliveryTime;
    private ValereSubmitter submitter;

}
