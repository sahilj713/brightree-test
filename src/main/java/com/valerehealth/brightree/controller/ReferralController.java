package com.valerehealth.brightree.controller;

import java.util.UUID;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.valerehealth.brightree.model.brightree.BrightreeReferralStatus;
import com.valerehealth.brightree.model.entity.RequestLog;
import com.valerehealth.brightree.model.valere.ValereDocument;
import com.valerehealth.brightree.model.valere.ValereOrder;
import com.valerehealth.brightree.service.BrightreeService;
import com.valerehealth.brightree.service.JsonService;
import com.valerehealth.brightree.service.RequestLogService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/")
public class ReferralController {

    @Autowired
    BrightreeService brightreeService;

    @Autowired
    RequestLogService requestLogService;

    @Autowired
    JsonService jsonService;

    @PostMapping("/referral")
    public ResponseEntity<String> createReferral(@Valid @RequestBody ValereOrder valereOrder) {
        RequestLog requestLog = requestLogService.createRquestLog("/referral", valereOrder.getId(),
                jsonService.toJsonOrString(valereOrder));

        try {
            brightreeService.createReferral(valereOrder, requestLog);
            requestLogService.updateRquestLog(requestLog);
            return ResponseEntity.ok("success");

        } catch (Exception e) {
            log.error("failed to send an order to brightree: " + valereOrder.getId(), e);
            requestLog.setResponseIn(e.getMessage());
            requestLogService.updateRquestLog(requestLog);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("failed: " + e.getMessage());
        }
    }

    @PostMapping("/referral/{orderId}/document")
    public ResponseEntity<String> createReferral(@Valid @PathVariable UUID orderId,
            @Valid @RequestBody ValereDocument valereDocument) {

        RequestLog requestLog = requestLogService.createRquestLog("/referral/{orderId}/document",
                orderId, jsonService.toJsonOrString(valereDocument));

        try {
            brightreeService.addReferralDocument(orderId, valereDocument, requestLog);
            requestLogService.updateRquestLog(requestLog);
            return ResponseEntity.ok("success");

        } catch (Exception e) {
            log.error("failed to send an add document to brightree: " + orderId, e);
            requestLog.setResponseIn(e.getMessage());
            requestLogService.updateRquestLog(requestLog);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("failed: " + e.getMessage());
        }
    }

    @GetMapping("/referral/{orderId}/status")
    public ResponseEntity<String> getOrderById(@Valid @PathVariable UUID orderId) {
        RequestLog requestLog =
                requestLogService.createRquestLog("/referral/{orderId}/status", orderId, null);

        try {
            BrightreeReferralStatus response =
                    brightreeService.getReferralStatus(orderId, requestLog);

            requestLogService.updateRquestLog(requestLog);
            return ResponseEntity.ok(response.getReferralStatus());
        } catch (Exception e) {
            log.error("failed to get referral status from brightree: " + orderId, e);
            requestLog.setResponseIn(e.getMessage());
            requestLogService.updateRquestLog(requestLog);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("failed: " + e.getMessage());
        }
    }

    @PostMapping("/site/{siteNickname}/referral/{referralKey}/updatestatus")
    public ResponseEntity<String> updateStatus(@Valid @PathVariable String siteNickname,
            @Valid @PathVariable Integer referralKey,
            @Valid @RequestBody BrightreeReferralStatus referralStatus) {
        return brightreeService.updateReferralStatus(siteNickname, referralKey, referralStatus);
    }

}
