package com.valerehealth.brightree.model.brightree;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import com.valerehealth.brightree.model.valere.ValereOrder;
import com.valerehealth.brightree.model.valere.ValereSubmitter;

public class BrightreePickupTest {

    @Test
    void testFromValereOrder_invalid() {
        ValereOrder valereOrder = new ValereOrder();
        assertNull(BrightreePickup.fromValereOrder(valereOrder));
    }

    @Test
    void testFromValereOrder_valid() {
        ValereSubmitter submitter = new ValereSubmitter();
        submitter.setFirstName("testFirst");
        submitter.setMiddleName("testMiddle");
        submitter.setLastName("testLast");

        ValereOrder valereOrder = new ValereOrder();
        valereOrder.setSubmitter(submitter);
        valereOrder.setRequestedPickupDate(LocalDate.parse("2023-01-13"));
        valereOrder.setRequestedPickupTime(LocalTime.parse("10:30:45"));
        valereOrder.setNotes(Arrays.asList("note1", "note2"));
        valereOrder.setPickupAllRentalItems(false);

        BrightreePickup pickup = BrightreePickup.fromValereOrder(valereOrder);

        assertEquals(pickup.getRequestedPickupDate(), "20230113");
        assertEquals(pickup.getRequestedPickupTime(), "10:30");
        assertEquals(pickup.getPickupAllRentalItems(), false);
        assertEquals(pickup.getNote(), "note1 note2");

        assertEquals(pickup.getSubmittedBy().getFirst(), "testFirst");
        assertEquals(pickup.getSubmittedBy().getMiddle(), "testMiddle");
        assertEquals(pickup.getSubmittedBy().getLast(), "testLast");
    }

}
