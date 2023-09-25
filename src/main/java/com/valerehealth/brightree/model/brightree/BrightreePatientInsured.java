package com.valerehealth.brightree.model.brightree;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class BrightreePatientInsured {

    // not_required
    private BrightreeName name;

    // not_required
    // Must be in the format MMDDCCYY, MMDDCCYYYY or CCYYMMDD.
    private String dob;

    // not_required
    // Must be a case-insensitive 'Male' or 'Female'.
    private String gender;

    // Postal Code required for automatic Doctor Creation.
    // not_required
    private BrightreeAddress address;

    // Must be 10 digits long and cannot contain dashes or parentheses for formatting.
    // not_required
    private String homePhone;

    // Must be 10 digits long and cannot contain dashes or parentheses for formatting.
    // not_required
    private String mobilePhone;

    // not_required
    private String employer;

    // not_required
    private String employeeContact;

    // not_required
    private BrightreeAddress employerAddress;

}
