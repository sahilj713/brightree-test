package com.valerehealth.brightree.model.brightree;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.valerehealth.brightree.model.valere.ValereOrder;
import com.valerehealth.brightree.model.valere.ValerePayer;
import com.valerehealth.brightree.util.BrightreeUtil;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class BrightreePatientPayor {

    // not_required
    private String expirationDate;

    // not_required
    private String externalPlanType;

    // Recommended
    // not_required
    private String groupName;

    // Recommended
    // not_required
    private String groupNumber;

    // not_required
    private String insuranceCompanyName;

    // Recommended
    // not_required
    private String insuranceId;

    // not_required
    private String insurancePlanName;

    // not_required
    private BrightreeAddress payorAddress;

    // The ID of the payor in the customer's Brightree database.
    // Conditional
    // not_required
    private Integer payorBrightreeId;

    // The ID of the payor in the partner system.
    // Conditional
    // not_required
    private String payorExternalId;

    // Must be "1", "2", or "3", representing primary, secondary and tertiary.
    // not_required
    private Integer payorLevel;

    // Recommended
    // not_required
    private String payorPhone;

    // Recommended
    // not_required
    private String policyNumber;

    // not_required
    // Must be one of:
    // Cadaver Donor, Child, Employee, Life Partner, Organ Donor, Other, Self, Spouse, Unknown
    private String relationship;

    // Recommended
    // not_required
    private String startDate;

    // Recommended
    // not_required
    private BrightreePatientInsured patientInsured;

    public static BrightreePatientPayor fromValerePayer(Integer level, ValerePayer valerePayer) {
        BrightreePatientPayor brightreePayor = new BrightreePatientPayor();

        brightreePayor.setPayorLevel(level);
        brightreePayor.setGroupNumber(valerePayer.getGroupNumber());
        brightreePayor.setInsuranceCompanyName(valerePayer.getPayerName());
        brightreePayor.setInsurancePlanName(valerePayer.getPlanName());
        brightreePayor.setPolicyNumber(valerePayer.getPolicyNumber());

        // TODO: mapping between third parties and brightree will be necessary
        brightreePayor.setInsuranceId(valerePayer.getInsuranceId());

        brightreePayor
                .setPayorPhone(BrightreeUtil.getPhoneNumber(valerePayer.getPayerPhoneNumber()));

        brightreePayor
                .setPayorAddress(BrightreeAddress.fromValereAddress(valerePayer.getPayerAddress()));

        brightreePayor.setRelationship(valerePayer.getRelationship());

        if (valerePayer.getInsuranceBeginDate() != null) {
            brightreePayor.setStartDate(
                    BrightreeUtil.localDateToYYYYMMDD(valerePayer.getInsuranceBeginDate()));
        }

        if (valerePayer.getInsuranceEndDate() != null) {
            brightreePayor.setExpirationDate(
                    BrightreeUtil.localDateToYYYYMMDD(valerePayer.getInsuranceEndDate()));
        }

        return brightreePayor;
    }

    public static List<BrightreePatientPayor> fromValereOrder(ValereOrder valereOrder) {
        if (valereOrder.getPayers() == null) {
            return null;
        }

        List<BrightreePatientPayor> patientPayors = new ArrayList<>();

        for (ValerePayer payor : valereOrder.getPayers()) {
            patientPayors.add(BrightreePatientPayor.fromValerePayer(
                    getBrightreePatientPayerLevel(payor.getPayerCategory()), payor));
        }

        return patientPayors;
    }

    private static Integer getBrightreePatientPayerLevel(String payorCategory) {
        if (payorCategory.equalsIgnoreCase("Primary")) {
            return 1;
        }

        if (payorCategory.equalsIgnoreCase("Secondary")) {
            return 2;
        }

        if (payorCategory.equalsIgnoreCase("Tertiary")) {
            return 3;
        }

        return 1;
    }

}
