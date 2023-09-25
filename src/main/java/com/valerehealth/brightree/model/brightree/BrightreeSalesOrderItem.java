package com.valerehealth.brightree.model.brightree;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.valerehealth.brightree.model.valere.ValereProduct;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class BrightreeSalesOrderItem {

    // The ID for the order line item in the partner's system.
    // This will populate the External ID (PriorSystemKey) field on the Sales Order Line Item.
    // not_required
    private String externalId;

    // The ID of the item in the Brightree database.
    // Either the ItemID or the externalItemId must be present.
    // Conditional
    private String itemId;

    // The local ID of the item in the partner system.
    // Either the ItemID or the externalItemId must be present.
    // Conditional
    private String externalItemId;

    // Recommended
    // not_required
    private String itemName;

    // Recommended
    // not_required
    private String itemDescription;

    // Recommended
    // not_required
    private Integer quantity;

    // not_required
    private String note;

    public static BrightreeSalesOrderItem fromValereProduct(ValereProduct valereProduct) {
        BrightreeSalesOrderItem salesOrderItem = new BrightreeSalesOrderItem();

        salesOrderItem.setExternalItemId(valereProduct.getId());
        salesOrderItem.setItemName(valereProduct.getName());
        salesOrderItem.setItemDescription(valereProduct.getDescription());
        salesOrderItem.setQuantity(valereProduct.getQuantity());

        return salesOrderItem;
    }

}
