package com.valerehealth.brightree.model.brightree;

import java.util.List;
import java.util.stream.Collectors;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.valerehealth.brightree.model.entity.OrderIdentifier;
import com.valerehealth.brightree.model.valere.ValereOrder;
import com.valerehealth.brightree.util.CommonUtil;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonInclude(Include.NON_NULL)
public class BrightreeCreateReferralRequest {

    // required
    private BrightreeFacility sendingFacility;

    // required
    private BrightreeFacility receivingFacility;

    // required
    private BrightreePatient patient;

    // not_required
    private List<BrightreeDocument> documents;

    // Sales Order and Pickup are mutually exclusive. A single referral can contain a sales order or
    // a referral but not both.
    // not_required
    private BrightreeSalesOrder salesOrder;

    // not_required
    private BrightreePickup pickup;

    public static BrightreeCreateReferralRequest fromValereOrder(OrderIdentifier orderIdentifier,
            ValereOrder valereOrder) {

        //How are these populated? Should order service send it?
        // Invalid: no pickup or salesOrder
        if (valereOrder.getRequestedPickupDate() == null
                && valereOrder.getRequestedDeliveryDate() == null) {
            return null;
        }

        // Invalid: pickup and salesOrder can't be present together
        if (valereOrder.getRequestedPickupDate() != null
                && valereOrder.getRequestedDeliveryDate() != null) {
            return null;
        }

        BrightreeCreateReferralRequest referralRequest = new BrightreeCreateReferralRequest();

        referralRequest.setSendingFacility(
                new BrightreeFacility(valereOrder.getOriginatorFacility().getId(),
                        valereOrder.getOriginatorFacility().getName()));

        referralRequest.setReceivingFacility(
                new BrightreeFacility(valereOrder.getIntegratorFacility().getId(),
                        valereOrder.getIntegratorFacility().getName()));

        referralRequest.setPatient(BrightreePatient
                .fromValerePatient(orderIdentifier.getGeneratedPatientId(), valereOrder));

        if (!CommonUtil.isEmpty(valereOrder.getDocuments())) {
            referralRequest.setDocuments(valereOrder.getDocuments().stream()
                    .map(valereDocument -> BrightreeDocument.fromValereDocument(valereDocument))
                    .collect(Collectors.toList()));
        }

        // Pickup order
        if (valereOrder.getRequestedPickupDate() != null) {
            referralRequest.setPickup(BrightreePickup.fromValereOrder(valereOrder));
        }

        // Sales order
        if (valereOrder.getRequestedDeliveryDate() != null) {
            referralRequest.setSalesOrder(BrightreeSalesOrder.fromValereOrder(valereOrder));
        }

        return referralRequest;
    }

}
