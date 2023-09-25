package com.valerehealth.brightree.model.brightree;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import com.valerehealth.brightree.model.entity.OrderIdentifier;
import com.valerehealth.brightree.model.valere.ValereFacility;
import com.valerehealth.brightree.model.valere.ValereOrder;
import com.valerehealth.brightree.model.valere.ValerePatient;

public class BrightreeCreateReferralRequestTest {

    @Test
    void testFromValereOrder_invalid_no_pickup_or_salesOrder() {
        ValereOrder valereOrder = new ValereOrder();

        assertNull(
                BrightreeCreateReferralRequest.fromValereOrder(new OrderIdentifier(), valereOrder));
    }

    @Test
    void testFromValereOrder_invalid_both_pickup_and_salesOrder() {
        ValereOrder valereOrder = new ValereOrder();

        valereOrder.setRequestedPickupDate(LocalDate.parse("2022-12-13"));
        valereOrder.setRequestedDeliveryDate(LocalDate.parse("2022-12-13"));

        assertNull(
                BrightreeCreateReferralRequest.fromValereOrder(new OrderIdentifier(), valereOrder));
    }

    @Test
    void testFromValereOrder_valid_pickup() {
        OrderIdentifier orderIdentifier = setupOrderIdentifier();
        ValereOrder valereOrder = setupOrder();

        valereOrder.setRequestedPickupDate(LocalDate.parse("2022-12-13"));
        valereOrder.setRequestedPickupTime(LocalTime.parse("10:30:45"));

        BrightreeCreateReferralRequest request =
                BrightreeCreateReferralRequest.fromValereOrder(orderIdentifier, valereOrder);

        assertNotNull(request.getSendingFacility());
        assertNotNull(request.getReceivingFacility());
        assertNotNull(request.getPatient());
        assertNotNull(request.getPickup());
        assertNull(request.getSalesOrder());
    }

    @Test
    void testFromValereOrder_valid_salesOrder() {
        OrderIdentifier orderIdentifier = setupOrderIdentifier();
        ValereOrder valereOrder = setupOrder();

        valereOrder.setRequestedDeliveryDate(LocalDate.parse("2022-12-13"));
        valereOrder.setRequestedDeliveryTime(LocalTime.parse("10:30:45"));

        BrightreeCreateReferralRequest request =
                BrightreeCreateReferralRequest.fromValereOrder(orderIdentifier, valereOrder);

        assertNotNull(request.getSendingFacility());
        assertNotNull(request.getReceivingFacility());
        assertNotNull(request.getPatient());
        assertNotNull(request.getSalesOrder());
        assertNull(request.getPickup());
    }

    private OrderIdentifier setupOrderIdentifier() {
        OrderIdentifier orderIdentifier = new OrderIdentifier();
        orderIdentifier.setGeneratedPatientId("testPatientId");
        return orderIdentifier;
    }

    private ValereOrder setupOrder() {
        ValereFacility valereFacility1 = new ValereFacility();
        valereFacility1.setId("testId1");
        valereFacility1.setId("testName1");

        ValereFacility valereFacility2 = new ValereFacility();
        valereFacility2.setId("testId2");
        valereFacility2.setName("testName2");

        ValerePatient valerePatient = new ValerePatient();
        valerePatient.setFirstName("testFirst");
        valerePatient.setLastName("testLast");

        ValereOrder valereOrder = new ValereOrder();
        valereOrder.setId(UUID.randomUUID());
        valereOrder.setPatient(valerePatient);
        valereOrder.setIntegratorFacility(valereFacility1);
        valereOrder.setOriginatorFacility(valereFacility2);

        return valereOrder;
    }

}
