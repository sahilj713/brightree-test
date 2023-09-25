package com.valerehealth.brightree.model.brightree;

import java.util.List;
import java.util.stream.Collectors;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.valerehealth.brightree.model.valere.ValereOrder;
import com.valerehealth.brightree.util.BrightreeUtil;
import com.valerehealth.brightree.util.CommonUtil;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class BrightreeSalesOrder {

    // The ID for the order in the partner's system.
    // This will populate the External ID (PriorSystemKey) field on the Sales Order.
    // not_required
    private String externalId;

    // Allowed values: [New, Delivered]
    // Default value: New
    // not_required
    private String status;

    // Format YYYYMMDD or MMDDYYYY.
    // not_required
    private String requestedDeliveryDate;

    // 24 Hr format HH:MM.
    // not_required
    private String requestedDeliveryTime;

    // Format YYYYMMDD or MMDDYYYY.
    // not_required
    private String actualDeliveryDate;

    // 24 Hr format HH:MM.
    // not_required
    private String actualDeliveryTime;

    // not_required
    private String note;

    // Recommended
    // not_required
    private BrightreeName submittedBy;

    // not_required
    private List<BrightreeSalesOrderItem> items;

    // not_required
    private BrightreeOrderingDoctor orderingDoctor;

    // not_required
    @JsonProperty("Diagnoses")
    private List<BrightreeDiagnosis> diagnoses;

    // See list of Valid Place of Service values below.
    // not_required
    private String placeOfService;

    // Defaults to false
    // not_required
    private Boolean printAmountOnDeliveryTicket;

    // Must represent a valid value in the Customer system.
    // not_required
    private Integer marketingRep;

    // Must represent a valid value in the Customer system.
    // not_required
    private Integer marketingReferral;

    // not_required
    private String deliveryNote;

    public static BrightreeSalesOrder fromValereOrder(ValereOrder valereOrder) {

        BrightreeSalesOrder salesOrder = new BrightreeSalesOrder();

        salesOrder.setExternalId(valereOrder.getId().toString());
        salesOrder.setStatus(BrightreeUtil.getSalesOrderStatus(valereOrder.getStatus()));

        salesOrder.setRequestedDeliveryDate(
                BrightreeUtil.localDateToYYYYMMDD(valereOrder.getRequestedDeliveryDate()));

        salesOrder.setRequestedDeliveryTime(
                BrightreeUtil.localTimeToHHMM(valereOrder.getRequestedDeliveryTime()));

        salesOrder.setActualDeliveryDate(
                BrightreeUtil.localDateToYYYYMMDD(valereOrder.getActualDeliveryDate()));

        salesOrder.setActualDeliveryTime(
                BrightreeUtil.localTimeToHHMM(valereOrder.getActualDeliveryTime()));

        salesOrder.setNote(CommonUtil.joinStrings(valereOrder.getNotes()));
        salesOrder.setSubmittedBy(BrightreeUtil.getSubmittedBy(valereOrder.getSubmitter()));

        if (!CommonUtil.isEmpty(valereOrder.getProducts())) {
            salesOrder.setItems(valereOrder.getProducts().stream()
                    .map(valereProduct -> BrightreeSalesOrderItem.fromValereProduct(valereProduct))
                    .collect(Collectors.toList()));
        }

        if (valereOrder.getPrescriber() != null) {
            salesOrder.setOrderingDoctor(
                    BrightreeOrderingDoctor.fromValerePrescriber(valereOrder.getPrescriber()));
        }

        if (valereOrder.getDiagnosisCodes() != null) {
            salesOrder.setDiagnoses(
                    BrightreeDiagnosis.fromValereDiagnosisCodes(valereOrder.getDiagnosisCodes()));
        }

        // TODO: validate values
        salesOrder.setPlaceOfService(valereOrder.getPlaceOfService());

        salesOrder.setPrintAmountOnDeliveryTicket(valereOrder.getPrintAmountOnDeliveryTicket());
        salesOrder.setMarketingRep(valereOrder.getMarketingRep());
        salesOrder.setMarketingReferral(valereOrder.getMarketingReferral());

        return salesOrder;
    }

    // Valid Place of Service values
    // 01 – Pharmacy
    // 02 – Telehealth
    // 03 – School
    // 04 – Homeless Shelter
    // 09 – Prison-Correctional Facility
    // 11 – Office
    // 12 – Home
    // 13 – Assisted Living Facility
    // 14 – Group Home
    // 15 – Mobile Unit
    // 16 – Temporary Lodging
    // 17 – Walk-In Retail Health Clinic
    // 20 – Urgent tary Treatment Facility
    // 21 – Inpatient Hospital
    // 22 – Outpatient Hospital
    // 23 – EmeCare Facility
    // 24 – Ambulatory Surgical Center
    // 25 – Birthing Center
    // 26 – Milirgency Room – Hospital
    // 31 – Skilled Nursing Facility
    // 32 – Nursing Facility
    // 33 – Custodial Care Facility
    // 34 – Hospice
    // 41 – Ambulance – Land
    // 42 – Ambulance – Air or Water
    // 49 – Independent Clinic
    // 50 – Federally qualified Health Center
    // 51 – Inpatient Psychiatric Facility
    // 52 – Psychiatric Facility partial Hospitalization
    // 53 – Community Mental Health Center
    // 54 – Intermediate Care Facility/Mentally Retarded
    // 55 – Residential Substance Abuse Treatment facility
    // 56 – Psychiatric Residential Treatment Center
    // 60 – Mass Immunization Center
    // 61 – Comprehensive Inpatient Rehabilitation Facility
    // 62 – Comprehensive Outpatient Rehabilitation Facility
    // 65 – End Stage Renal Disease Treatment Facility
    // 71 – State or Local Public Health Clinic
    // 72 – Rural Health Clinic
    // 81 – Independent Laboratory
    // 99 – Other Unlisted Facility

}
