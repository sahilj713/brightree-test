package com.valerehealth.brightree.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import com.valerehealth.brightree.model.brightree.BrightreeReferralStatus;
import com.valerehealth.brightree.model.entity.OrderIdentifier;
import com.valerehealth.brightree.model.entity.RequestLog;
import com.valerehealth.brightree.model.valere.ValereDocument;
import com.valerehealth.brightree.model.valere.ValereFacility;
import com.valerehealth.brightree.model.valere.ValereOrder;
import com.valerehealth.brightree.model.valere.ValerePatient;
import com.valerehealth.brightree.model.valere.ValereSubmitter;
import com.valerehealth.brightree.repository.RequestLogRepository;

@SpringBootTest
public class BrightreeServiceIntegrationTest {

    @Autowired
    OrderIdentifierService orderIdentifierService;

    @Autowired
    BrightreeService brightreeService;

    @Autowired
    RequestLogService requestLogService;

    @Autowired
    RequestLogRepository requestLogRepository;

    @Value("${brightree.site-nickname}")
    String brightreeSiteNickname;

    @Test
    void testCreateReferral_duplicate_order() {
        ValereOrder valereOrder = createOrder(UUID.randomUUID());

        RequestLog requestLog = requestLogService.createRquestLog("/test/referral",
                valereOrder.getId(), valereOrder.toString());

        createOrderIdentifier(valereOrder);

        assertThrowsExactly(RuntimeException.class,
                () -> brightreeService.createReferral(valereOrder, requestLog),
                "Expected createReferral() to throw an execption, but it didn't!");
    }

    @Test
    void testCreateReferral_invalid_order() {
        UUID orderId = UUID.randomUUID();
        ValereOrder valereOrder = createOrder(orderId);

        RequestLog requestLog = requestLogService.createRquestLog("/test/referral",
                valereOrder.getId(), valereOrder.toString());

        assertThrowsExactly(RuntimeException.class,
                () -> brightreeService.createReferral(valereOrder, requestLog),
                "Expected createReferral() to throw an execption, but it didn't!");

        assertNotNull(orderIdentifierService.getOrderIdentifier(orderId));
    }

    @Test
    void testCreateReferral_valid_order() {
        UUID orderId = UUID.randomUUID();
        RequestLog requestLog = requestLogService.createRquestLog("/test/referral", orderId, null);

        assertDoesNotThrow(
                () -> brightreeService.createReferral(createFullOrder(orderId), requestLog),
                "Expected createReferral() to not throw an execption, but it did!");

        OrderIdentifier orderIdentifier = orderIdentifierService.getOrderIdentifier(orderId);

        assertNotNull(orderIdentifier);
        assertNotNull(orderIdentifier.getReferralKey());
    }

    @Test
    void testGetReferralStatus_invalid() {
        UUID orderId = UUID.randomUUID();
        RequestLog requestLog = requestLogService.createRquestLog("/test/getStatus", orderId, null);

        assertThrowsExactly(RuntimeException.class,
                () -> brightreeService.getReferralStatus(orderId, requestLog),
                "Expected getReferralStatus() to throw an execption, but it didn't!");
    }

    @Test
    void testGetReferralStatus_no_referral_key() {
        UUID orderId = UUID.randomUUID();
        ValereOrder valereOrder = createOrder(orderId);
        orderIdentifierService.createOrderIdentifier(valereOrder);
        RequestLog requestLog = requestLogService.createRquestLog("/test/getStatus", orderId, null);

        assertThrowsExactly(RuntimeException.class,
                () -> brightreeService.getReferralStatus(orderId, requestLog),
                "Expected getReferralStatus() to throw an execption, but it didn't!");
    }

    @Test
    void testAddReferralDocument_invalid() {
        UUID orderId = UUID.randomUUID();

        RequestLog requestLog =
                requestLogService.createRquestLog("/test/addDocument", orderId, null);

        assertThrowsExactly(RuntimeException.class,
                () -> brightreeService.addReferralDocument(orderId, createDocument(), requestLog),
                "Expected addReferralDocument() to throw an execption, but it didn't!");
    }

    @Test
    void testAddReferralDocument_no_referral_key() {
        UUID orderId = UUID.randomUUID();
        ValereOrder valereOrder = createOrder(orderId);
        orderIdentifierService.createOrderIdentifier(valereOrder);

        RequestLog requestLog =
                requestLogService.createRquestLog("/test/addDocument", orderId, null);

        assertThrowsExactly(RuntimeException.class,
                () -> brightreeService.addReferralDocument(orderId, createDocument(), requestLog),
                "Expected addReferralDocument() to throw an execption, but it didn't!");
    }

    @Test
    void testAddReferralDocument_valid() throws Exception {
        UUID orderId = UUID.randomUUID();

        RequestLog requestLog1 = requestLogService.createRquestLog("/test/referral", orderId, null);
        brightreeService.createReferral(createFullOrder(orderId), requestLog1);

        RequestLog requestLog2 =
                requestLogService.createRquestLog("/test/addDocument", orderId, null);

        assertDoesNotThrow(
                () -> brightreeService.addReferralDocument(orderId, createDocument(), requestLog2),
                "Expected addReferralDocument() to not throw an execption, but it did!");
    }

    @Test
    void testGetReferralStatus_valid() throws Exception {
        UUID orderId = UUID.randomUUID();

        RequestLog requestLog1 = requestLogService.createRquestLog("/test/referral", orderId, null);

        RequestLog requestLog2 =
                requestLogService.createRquestLog("/test/addDocument", orderId, null);

        brightreeService.createReferral(createFullOrder(orderId), requestLog1);
        BrightreeReferralStatus response = brightreeService.getReferralStatus(orderId, requestLog2);

        assertNotNull(response.getReferralStatus());
    }

    @Test
    void testUpdateReferralStatus_null_siteNickname() {
        testUpdateReferralStatus(null, getRandomReferralKey(), null, "Invalid sitenickname");
    }

    @Test
    void testUpdateReferralStatus_invalid_siteNickname() {
        testUpdateReferralStatus("invalid_nickname", getRandomReferralKey(), null,
                "Invalid sitenickname");
    }

    @Test
    void testUpdateReferralStatus_null_referralKey() {
        testUpdateReferralStatus(brightreeSiteNickname, null, null, "Invalid referralKey");

    }

    @Test
    void testUpdateReferralStatus_invalid_referralKey() {
        testUpdateReferralStatus(brightreeSiteNickname, getRandomReferralKey(),
                new BrightreeReferralStatus(), "accepted with warning");
    }

    @Test
    void testUpdateReferralStatus_valid() {
        UUID orderId = UUID.randomUUID();
        ValereOrder valereOrder = createOrder(orderId);
        OrderIdentifier orderIdentifier = createOrderIdentifier(valereOrder);
        BrightreeReferralStatus status = new BrightreeReferralStatus();
        status.setReferralStatus("Accepted");

        testUpdateReferralStatus(brightreeSiteNickname, orderIdentifier.getReferralKey(), status,
                "success");

        assertTrue(requestLogRepository.findByOrderId(orderId).getRequestIn()
                .contains("referralStatus=Accepted"));
    }

    private String testUpdateReferralStatus(String siteNickname, Integer referralKey,
            BrightreeReferralStatus status, String expectedResponse) {
        String response =
                brightreeService.updateReferralStatus(siteNickname, referralKey, status).getBody();

        assertEquals(response, expectedResponse);
        return response;
    }

    private ValereDocument createDocument() {
        ValereDocument valereDocument = new ValereDocument();

        valereDocument.setId("testId");
        valereDocument.setContent("testContent");
        valereDocument.setType("testType");

        return valereDocument;
    }

    private ValereOrder createOrder(UUID orderId) {
        ValerePatient valerePatient = new ValerePatient();
        valerePatient.setId("321");

        ValereOrder valereOrder = new ValereOrder();
        valereOrder.setId(orderId);
        valereOrder.setOriginatorId("testOriginatorId");
        valereOrder.setOriginatorOrderId("123");
        valereOrder.setPatient(valerePatient);

        return valereOrder;
    }

    private ValereOrder createFullOrder(UUID orderId) {
        ValereOrder valereOrder = createOrder(orderId);

        valereOrder.getPatient().setFirstName("testFirst");
        valereOrder.getPatient().setLastName("testLast");

        ValereSubmitter submitter = new ValereSubmitter();
        submitter.setFirstName("testFirst");
        submitter.setMiddleName("testMiddle");
        submitter.setLastName("testLast");

        ValereFacility valereFacility1 = new ValereFacility();
        valereFacility1.setId("testId1");
        valereFacility1.setId("testName1");

        ValereFacility valereFacility2 = new ValereFacility();
        valereFacility2.setId("testId2");
        valereFacility2.setName("testName2");

        valereOrder.setSubmitter(submitter);
        valereOrder.setIntegratorFacility(valereFacility1);
        valereOrder.setOriginatorFacility(valereFacility2);
        valereOrder.setRequestedPickupDate(LocalDate.parse("2023-01-13"));
        valereOrder.setRequestedPickupTime(LocalTime.parse("10:30:45"));
        valereOrder.setNotes(Arrays.asList("note1", "note2"));
        valereOrder.setPickupAllRentalItems(false);

        return valereOrder;
    }

    private OrderIdentifier createOrderIdentifier(ValereOrder valereOrder) {
        OrderIdentifier orderIdentifier = orderIdentifierService.createOrderIdentifier(valereOrder);
        orderIdentifier.setReferralKey(getRandomReferralKey());
        return orderIdentifierService.updateOrderIdentifier(orderIdentifier);
    }

    private int getRandomReferralKey() {
        return 1 + (int) (Math.random() * (1002));
    }

}
