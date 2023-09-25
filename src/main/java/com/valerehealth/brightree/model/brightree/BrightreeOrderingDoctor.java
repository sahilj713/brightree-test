package com.valerehealth.brightree.model.brightree;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.valerehealth.brightree.model.valere.ValereAddress;
import com.valerehealth.brightree.model.valere.ValerePrescriber;
import com.valerehealth.brightree.util.BrightreeUtil;
import com.valerehealth.brightree.util.CommonUtil;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class BrightreeOrderingDoctor {

    // Conditional
    // The ID of the doctor in the customer's database.
    private Integer brightreeId;

    // Conditional
    // Must be 10 characters long.
    private String npi;

    // Conditional
    // Required for automatic Doctor Creation.
    private BrightreeName name;

    // Conditional
    // Postal Code required for automatic Doctor Creation.
    private BrightreeAddress address;

    // not_required
    // Must be 10 digits long and cannot contain dashes or parentheses for formatting.
    private String faxNumber;

    // not_required
    // Must be 10 digits long and cannot contain dashes or parentheses for formatting.
    private String phone;

    // not_required
    // Must be 10 digits long and cannot contain dashes or parentheses for formatting.
    private String mobilePhone;

    public static BrightreeOrderingDoctor fromValerePrescriber(ValerePrescriber valerePrescriber) {
        if (valerePrescriber == null) {
            return null;
        }

        BrightreeName orderingDoctorName = new BrightreeName();
        orderingDoctorName.setFirst(valerePrescriber.getFirstName());
        orderingDoctorName.setMiddle(valerePrescriber.getMiddleName());
        orderingDoctorName.setLast(valerePrescriber.getLastName());

        BrightreeOrderingDoctor orderingDoctor = new BrightreeOrderingDoctor();
        orderingDoctor.setNpi(valerePrescriber.getNpi());
        orderingDoctor.setName(orderingDoctorName);
        orderingDoctor.setPhone(BrightreeUtil.getPhoneNumber(valerePrescriber.getPhoneNumber()));
        orderingDoctor.setFaxNumber(BrightreeUtil.getPhoneNumber(valerePrescriber.getFax()));

        ValereAddress prescriberAddress = valerePrescriber.getPrescriberAddress();
        if (prescriberAddress != null
                && CommonUtil.hasValue(prescriberAddress.getAddressLine1())
                && CommonUtil.hasValue(prescriberAddress.getCity())
                && CommonUtil.hasValue(prescriberAddress.getState())
                && CommonUtil.hasValue(prescriberAddress.getZipCode())) {

            BrightreeAddress orderingDoctorAddress = new BrightreeAddress();
            orderingDoctorAddress.setAddressLine1(prescriberAddress.getAddressLine1());
            orderingDoctorAddress.setAddressLine2(prescriberAddress.getAddressLine2());
            orderingDoctorAddress.setCity(prescriberAddress.getCity());
            orderingDoctorAddress.setState(prescriberAddress.getState());
            orderingDoctorAddress
                    .setZipCode(BrightreeUtil.getZipCode(prescriberAddress.getZipCode()));
            orderingDoctorAddress.setCountry(prescriberAddress.getCountry());

            orderingDoctor.setAddress(orderingDoctorAddress);
        }

        return orderingDoctor;
    }

}
