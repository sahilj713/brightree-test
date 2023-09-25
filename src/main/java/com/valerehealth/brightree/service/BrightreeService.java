package com.valerehealth.brightree.service;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.valerehealth.brightree.model.brightree.BrightreeAuthRequest;
import com.valerehealth.brightree.model.brightree.BrightreeAuthResponse;
import com.valerehealth.brightree.model.brightree.BrightreeCreateReferralRequest;
import com.valerehealth.brightree.model.brightree.BrightreeCreateReferralResponse;
import com.valerehealth.brightree.model.brightree.BrightreeDocument;
import com.valerehealth.brightree.model.brightree.BrightreeReferralStatus;
import com.valerehealth.brightree.model.entity.OrderIdentifier;
import com.valerehealth.brightree.model.entity.RequestLog;
import com.valerehealth.brightree.model.valere.ValereDocument;
import com.valerehealth.brightree.model.valere.ValereOrder;
import com.valerehealth.brightree.util.CommonUtil;

@Service
public class BrightreeService {

    @Autowired
    OrderIdentifierService orderIdentifierService;

    @Autowired
    RequestLogService requestLogService;

    @Autowired
    JsonService jsonService;

    private String brightreeServer;
    private String brightreeSiteNickname;
    private String clientId;
    private String clientSecret;
    private RestTemplate restTemplate;
    private String brightreeToken;

    public BrightreeService(@Value("${brightree.server}") String brightreeServer,
            @Value("${brightree.site-nickname}") String brightreeSiteNickname,
            @Value("${brightree.client-id}") String clientId,
            @Value("${brightree.client-secret}") String clientSecret,
            RestTemplateBuilder restTemplateBuilder) {

        this.brightreeServer = brightreeServer;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.brightreeSiteNickname = brightreeSiteNickname;

        this.restTemplate = restTemplateBuilder.build();
    }

    public BrightreeCreateReferralResponse createReferral(ValereOrder valereOrder,
            RequestLog requestLog) throws Exception {

        OrderIdentifier orderIdentifier = orderIdentifierService.createOrderIdentifier(valereOrder);

        // Duplicate
        if (orderIdentifier == null) {
            throw new RuntimeException("Duplicate order");
        }

        BrightreeCreateReferralRequest referralRequest =
                BrightreeCreateReferralRequest.fromValereOrder(orderIdentifier, valereOrder);

        if (referralRequest == null) {
            throw new RuntimeException("Invalid order");
        }

        requestLog.setRequestOut(jsonService.toJsonOrString(referralRequest));
        String token = getTokenString(false);

        ResponseEntity<BrightreeCreateReferralResponse> responseEntity = null;

        try {
            responseEntity = sendCreateReferralRequest(token, referralRequest);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
                token = getTokenString(true);
                responseEntity = sendCreateReferralRequest(token, referralRequest);
            }
        }

        if (responseEntity == null) {
            throw new RuntimeException("Failed to send a referral");
        }

        BrightreeCreateReferralResponse response = responseEntity.getBody();

        if (response == null) {
            throw new RuntimeException("Failed to get responseBody from createReferral response");
        }

        // TODO: handle response.messages if need to
        orderIdentifier.setReferralKey(response.getReferralKey());
        orderIdentifierService.updateOrderIdentifier(orderIdentifier);
        requestLog.setResponseIn(jsonService.toJsonOrString(response));

        return response;
    }

    public void addReferralDocument(UUID orderId, ValereDocument valereDocument,
            RequestLog requestLog) throws Exception {

        OrderIdentifier orderIdentifier = orderIdentifierService.getOrderIdentifier(orderId);

        if (orderIdentifier == null) {
            throw new RuntimeException("Invalid orderId");
        }

        if (orderIdentifier.getReferralKey() == null) {
            throw new RuntimeException("Order doesn't have a referralKey");
        }

        BrightreeDocument addDocumentRequest = BrightreeDocument.fromValereDocument(valereDocument);

        if (addDocumentRequest == null) {
            throw new RuntimeException("Invalid request");
        }

        requestLog.setRequestOut(jsonService.toJsonOrString(addDocumentRequest));
        String token = getTokenString(false);
        ResponseEntity<String> responseEntity = null;

        try {
            responseEntity = sendAddReferralDocument(token, orderIdentifier.getReferralKey(),
                    addDocumentRequest);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
                token = getTokenString(true);

                responseEntity = sendAddReferralDocument(token, orderIdentifier.getReferralKey(),
                        addDocumentRequest);
            }
        }

        if (responseEntity == null) {
            throw new RuntimeException("Failed to get referral status");
        }

        if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            throw new RuntimeException(responseEntity.getBody());
        }
    }

    public BrightreeReferralStatus getReferralStatus(UUID orderId, RequestLog requestLog)
            throws Exception {
        OrderIdentifier orderIdentifier = orderIdentifierService.getOrderIdentifier(orderId);

        if (orderIdentifier == null) {
            throw new RuntimeException("Invalid orderId");
        }

        if (orderIdentifier.getReferralKey() == null) {
            throw new RuntimeException("Order doesn't have a referralKey");
        }

        String token = getTokenString(false);
        ResponseEntity<BrightreeReferralStatus> responseEntity = null;

        try {
            responseEntity = sendGetReferralStatusRequest(token, orderIdentifier.getReferralKey());
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
                token = getTokenString(true);

                responseEntity =
                        sendGetReferralStatusRequest(token, orderIdentifier.getReferralKey());
            }
        }

        if (responseEntity == null) {
            throw new RuntimeException("Failed to get referral status");
        }

        BrightreeReferralStatus response = responseEntity.getBody();

        if (response == null) {
            throw new RuntimeException("Failed to get responseBody from createReferral response");
        }

        requestLog.setResponseIn(jsonService.toJsonOrString(response));
        return response;
    }

    public ResponseEntity<String> updateReferralStatus(String siteNickname, Integer referralKey,
            BrightreeReferralStatus referralStatus) {

        if (!CommonUtil.hasValue(siteNickname) || !siteNickname.equals(brightreeSiteNickname)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid sitenickname");
        }

        if (referralKey == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid referralKey");
        }

        OrderIdentifier orderIdentifier =
                orderIdentifierService.getOrderIdentifierByReferralKey(referralKey);

        UUID orderId = null;
        String response = "accepted with warning";

        if (orderIdentifier != null) {
            response = "success";
            orderId = orderIdentifier.getOrderId();
        }

        RequestLog requestLog = requestLogService.createRquestLog(
                "/site/{siteNickname}/referral/{referralId}/updatestatus", orderId,
                referralStatus.toString());

        // Storing notes on requestOut
        requestLog.setRequestOut(response);
        requestLogService.updateRquestLog(requestLog);

        return ResponseEntity.ok(response);
    }

    private ResponseEntity<BrightreeCreateReferralResponse> sendCreateReferralRequest(String token,
            BrightreeCreateReferralRequest referralRequest) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "bearer " + token);

        HttpEntity<BrightreeCreateReferralRequest> requestEntity =
                new HttpEntity<>(referralRequest, headers);

        return restTemplate.postForEntity(
                brightreeServer + "/api/site/" + brightreeSiteNickname + "/referral", requestEntity,
                BrightreeCreateReferralResponse.class);
    }

    private ResponseEntity<String> sendAddReferralDocument(String token, Integer referralKey,
            BrightreeDocument addDocumentrequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "bearer " + token);

        HttpEntity<BrightreeDocument> requestEntity = new HttpEntity<>(addDocumentrequest, headers);

        return restTemplate.postForEntity(brightreeServer + "/api/site/" + brightreeSiteNickname
                + "/referral/" + referralKey + "/document", requestEntity, String.class);
    }

    private ResponseEntity<BrightreeReferralStatus> sendGetReferralStatusRequest(String token,
            Integer referralKey) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "bearer " + token);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        return restTemplate.exchange(
                brightreeServer + "/api/site/" + brightreeSiteNickname + "/referral/" + referralKey
                        + "/getreferralstatus",
                HttpMethod.GET, requestEntity, BrightreeReferralStatus.class);
    }

    private String getTokenString(boolean shouldRetry) throws Exception {
        if (!CommonUtil.hasValue(brightreeToken) || shouldRetry) {
            brightreeToken = getBrightreeToken().getAccessToken();
        }

        return brightreeToken;
    }

    private BrightreeAuthResponse getBrightreeToken() throws Exception {
        BrightreeAuthRequest authRequest = new BrightreeAuthRequest();
        HttpHeaders headers = new HttpHeaders();

        authRequest.setClientId(clientId);
        authRequest.setClientSecret(clientSecret);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> requestEntity =
                new HttpEntity<>(authRequest.toMultiValueMap(), headers);

        ResponseEntity<BrightreeAuthResponse> responseEntity = restTemplate.postForEntity(
                brightreeServer + "/auth/token", requestEntity, BrightreeAuthResponse.class);

        BrightreeAuthResponse response = responseEntity.getBody();

        if (response == null) {
            throw new RuntimeException("Failed to get responseBody from getToken response");
        }

        return response;
    }

}
