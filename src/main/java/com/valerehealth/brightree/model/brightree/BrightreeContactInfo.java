package com.valerehealth.brightree.model.brightree;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class BrightreeContactInfo {

    // Postal Code required for automatic Doctor Creation.
    // not_required
    private BrightreeAddress address;

    // Must contain an "@" sign and at least a 2-level domain after the "'@".
    // not_required
    private String emailAddress;

    // Must be 10 digits long and cannot contain dashes or parentheses for formatting.
    // not_required
    private String faxNumber;

    // Must be 10 digits long and cannot contain dashes or parentheses for formatting.
    // not_required
    private String homePhone;

    // Must be 10 digits long and cannot contain dashes or parentheses for formatting.
    // not_required
    private String mobilePhone;

    // Recomended
    private BrightreeName name;

    // required
    // Valid Emergency Contact Relationship values:
    // [Child, Declined, Friend, Life Partner, Neighbor, Other, Parent, Sibling]
    private String relationship;

}
