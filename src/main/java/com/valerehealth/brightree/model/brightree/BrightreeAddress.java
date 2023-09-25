package com.valerehealth.brightree.model.brightree;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.valerehealth.brightree.model.valere.ValereAddress;
import com.valerehealth.brightree.util.BrightreeUtil;
import com.valerehealth.brightree.util.CommonUtil;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class BrightreeAddress {

    // not_required
    private String addressLine1;

    // not_required
    private String addressLine2;

    // not_required
    private String city;

    // Must be the 2-letter abbreviation of one of the 50 US States or "DC" for the District of
    // Columbia.
    // not_required
    private String state;

    // not_required
    private String county;

    // Must be "USA", "Can" or "Mex".
    // not_required
    private String country;

    // Must be 5 or 9 digits. ZIP + 4 codes must not include a dash.
    // not_required
    private String zipCode;

    public static BrightreeAddress fromValereAddress(ValereAddress valereAddress) {
        if (valereAddress == null || !CommonUtil.hasValue(valereAddress.getAddressLine1())
                || !CommonUtil.hasValue(valereAddress.getCity())
                || !CommonUtil.hasValue(valereAddress.getState())
                || !CommonUtil.hasValue(valereAddress.getZipCode())) {
            return null;
        }

        BrightreeAddress address = new BrightreeAddress();

        address.setAddressLine1(valereAddress.getAddressLine1());
        address.setAddressLine2(valereAddress.getAddressLine2());
        address.setCity(valereAddress.getCity());
        address.setState(valereAddress.getState());
        address.setCountry(valereAddress.getCountry());
        address.setZipCode(BrightreeUtil.getZipCode(valereAddress.getZipCode()));

        return address;
    }

}
