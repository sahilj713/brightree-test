package com.valerehealth.brightree.model.brightree;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.valerehealth.brightree.model.valere.ValereOrder;
import com.valerehealth.brightree.util.BrightreeUtil;
import com.valerehealth.brightree.util.CommonUtil;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class BrightreePickup {

    // Format YYYYMMDD or MMDDYYYY
    // not_required
    private String requestedPickupDate;

    // 24 Hour format HH:MM
    // not_required
    private String requestedPickupTime;

    // not_required
    private String note;

    // not_required
    private BrightreeName submittedBy;

    // “true” or “false”. If property is not provided in the document or it is not valid, this value
    // will be set to “false”.
    // not_required
    private Boolean pickupAllRentalItems;

    public static BrightreePickup fromValereOrder(ValereOrder valereOrder) {
        if (valereOrder.getRequestedPickupDate() == null) {
            return null;
        }

        BrightreePickup pickup = new BrightreePickup();

        pickup.setRequestedPickupDate(
                BrightreeUtil.localDateToYYYYMMDD(valereOrder.getRequestedPickupDate()));

        pickup.setRequestedPickupTime(
                BrightreeUtil.localTimeToHHMM(valereOrder.getRequestedPickupTime()));

        pickup.setNote(CommonUtil.joinStrings(valereOrder.getNotes()));
        pickup.setSubmittedBy(BrightreeUtil.getSubmittedBy(valereOrder.getSubmitter()));
        pickup.setPickupAllRentalItems(valereOrder.getPickupAllRentalItems());

        return pickup;
    }

}
