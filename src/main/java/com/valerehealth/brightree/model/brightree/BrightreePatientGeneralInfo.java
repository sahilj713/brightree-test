package com.valerehealth.brightree.model.brightree;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class BrightreePatientGeneralInfo {

    // required
    private BrightreeName name;

    // Must be in the format MMDDCCYY, MMDDCCYYYY or CCYYMMDD.
    // not_required
    private String dob;

    // not_required
    private BrightreeAddress billingAddress;

    // not_required
    private BrightreeAddress deliveryAddress;

    // Must be a case-insensitive 'Male' or 'Female'.
    // not_required
    private String gender;

    // Must be one of "Single", "Married", "Common-law", "Divorced", "Widowed" or "Unknown".
    // not_required
    private String maritalStatus;

    // Must be 9 digits long and cannot contain dashes for formatting.
    // not_required
    private String ssn;

    // Must be 10 digits long and cannot contain dashes or parentheses for formatting.
    // not_required
    private String homePhone;

    // Must be 10 digits long and cannot contain dashes or parentheses for formatting.
    // not_required
    private String mobilePhone;

    // Must contain an "@" sign and at least a 2-level domain after the "'@".
    // not_required
    private String emailAddress;

}
