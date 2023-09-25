package com.valerehealth.brightree.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.valerehealth.brightree.model.entity.OrderIdentifier;
import com.valerehealth.brightree.model.valere.ValereOrder;
import com.valerehealth.brightree.model.valere.ValerePatient;

@SpringBootTest
public class OrderIdentifierServiceIntegrationTest {

    @Autowired
    OrderIdentifierService orderIdentifierService;

    @Test
    void testCreateOrderIdentifier_newOrder() {
        UUID orderId = UUID.randomUUID();
        OrderIdentifier orderIdentifier = createOrderAndOrderIdentifier(orderId);

        assertNotNull(orderIdentifier);
        assertNotNull(orderIdentifierService.getOrderIdentifier(orderId));
    }

    @Test
    void testCreateOrderIdentifier_no_referral_key() {
        UUID orderId = UUID.randomUUID();
        OrderIdentifier orderIdentifier1 = createOrderAndOrderIdentifier(orderId);
        OrderIdentifier orderIdentifier2 = createOrderAndOrderIdentifier(orderId);

        assertEquals(orderIdentifier1.getId(), orderIdentifier2.getId());
        assertEquals(orderIdentifier1.getOrderId(), orderIdentifier2.getOrderId());
        assertEquals(orderIdentifier1.getReferralKey(), orderIdentifier2.getReferralKey());

        assertEquals(orderIdentifier1.getGeneratedOrderId(),
                orderIdentifier2.getGeneratedOrderId());

        assertEquals(orderIdentifier1.getGeneratedPatientId(),
                orderIdentifier2.getGeneratedPatientId());
    }

    @Test
    void testCreateOrderIdentifier_with_referral_key() {
        UUID orderId = UUID.randomUUID();
        OrderIdentifier orderIdentifier1 = createOrderAndOrderIdentifier(orderId);
        orderIdentifier1.setReferralKey(123);
        orderIdentifier1 = orderIdentifierService.updateOrderIdentifier(orderIdentifier1);
        OrderIdentifier orderIdentifier2 = createOrderAndOrderIdentifier(orderId);

        assertNotEquals(orderIdentifier1, orderIdentifier2);
        assertNull(orderIdentifier2);
    }

    @Test
    void testUpdateOrderIdentifier() {
        UUID orderId = UUID.randomUUID();
        OrderIdentifier orderIdentifier = createOrderAndOrderIdentifier(orderId);
        Long previousUpdatedAt = orderIdentifier.getUpdatedAt().toEpochMilli();
        orderIdentifier.setReferralKey(123);

        OrderIdentifier newOrderIdentifier =
                orderIdentifierService.updateOrderIdentifier(orderIdentifier);

        assertTrue(newOrderIdentifier.getUpdatedAt().toEpochMilli() > previousUpdatedAt);
    }

    private OrderIdentifier createOrderAndOrderIdentifier(UUID orderId) {
        ValerePatient valerePatient = new ValerePatient();
        valerePatient.setId("321");

        ValereOrder valereOrder = new ValereOrder();
        valereOrder.setId(orderId);
        valereOrder.setOriginatorId("testOriginatorId");
        valereOrder.setOriginatorOrderId("123");
        valereOrder.setPatient(valerePatient);

        return orderIdentifierService.createOrderIdentifier(valereOrder);
    }

}
