package com.valerehealth.brightree.model.brightree;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.valerehealth.brightree.model.valere.ValereOrder;
import com.valerehealth.brightree.model.valere.ValerePatient;
import com.valerehealth.brightree.util.BrightreeUtil;
import com.valerehealth.brightree.util.CommonUtil;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class BrightreePatient {

    // not_required
    private Integer brightreeId;

    // not_required
    private Integer patientId;

    // not_required
    private String externalId;

    // required
    private BrightreePatientGeneralInfo generalInfo;

    // not_required
    private BrightreePatientClinicalInfo clinicalInfo;

    // not_required
    private BrightreeContactInfo emergencyContact;

    // not_required
    private BrightreeContactInfo responsibleParty;

    // not_required
    private BrightreeInsuranceInfo insuranceInfo;

    // Must be either "Patient" or "FacilityResident" The default the value is “Patient”
    // not_required
    private String customerType;

    // Conditional
    // Required when customerType = "FacilityResident" and null when customerType = "Patient".
    // When specified must be the patientId of an already existing Brightree patient that is a
    // “FacilityMaster”.
    // The FacilityResident created from the referral will be associated with the same facility
    // associated as this patient.
    // not_required
    private Integer facilityMasterId;

    public static BrightreePatient fromValerePatient(String generatedPatientId,
            ValereOrder valereOrder) {

        ValerePatient valerePatient = valereOrder.getPatient();
        BrightreeName name = new BrightreeName();
        name.setFirst(valerePatient.getFirstName());
        name.setMiddle(valerePatient.getMiddleName());
        name.setLast(valerePatient.getLastName());

        BrightreePatientGeneralInfo generalInfo = new BrightreePatientGeneralInfo();
        generalInfo.setName(name);
        generalInfo.setDob(BrightreeUtil.localDateToYYYYMMDD(valerePatient.getDateOfBirth()));
        generalInfo.setGender(BrightreeUtil.getGender(valerePatient.getGender()));

        BrightreeAddress patientAddress =
                BrightreeAddress.fromValereAddress(valerePatient.getPatientAddress());

        BrightreeAddress patientFulfillmentAddress =
                BrightreeAddress.fromValereAddress(valerePatient.getFulfillmentAddress());

        generalInfo.setBillingAddress(patientAddress);

        generalInfo.setDeliveryAddress(
                patientFulfillmentAddress != null ? patientFulfillmentAddress : patientAddress);

        generalInfo
                .setMaritalStatus(BrightreeUtil.getMaritalStatus(valerePatient.getMaritalStatus()));

        generalInfo.setSsn(BrightreeUtil.getSsn(valerePatient.getSsn()));
        generalInfo.setHomePhone(BrightreeUtil.getPhoneNumber(valerePatient.getHomePhoneNumber()));

        generalInfo
                .setMobilePhone(BrightreeUtil.getPhoneNumber(valerePatient.getCellPhoneNumber()));

        generalInfo.setEmailAddress(BrightreeUtil.getEmail(valerePatient.getEmail()));

        BrightreePatient brightreePatient = new BrightreePatient();
        brightreePatient.setExternalId(generatedPatientId);
        brightreePatient.setGeneralInfo(generalInfo);

        List<BrightreePatientPayor> patientPayors =
                BrightreePatientPayor.fromValereOrder(valereOrder);

        if (!CommonUtil.isEmpty(patientPayors)) {
            brightreePatient.setInsuranceInfo(new BrightreeInsuranceInfo(patientPayors));
        }

        brightreePatient
                .setClinicalInfo(BrightreePatientClinicalInfo.fromValereOrder(valereOrder));

        // TODO
        // brightreePatient.setEmergencyContact(null);
        // brightreePatient.setResponsibleParty(null);
        // brightreePatient.setCustomerType(null);
        // brightreePatient.setFacilityMasterId(null);

        return brightreePatient;
    }

}

