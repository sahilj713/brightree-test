package com.valerehealth.brightree.service;

import java.time.Instant;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.valerehealth.brightree.model.entity.RequestLog;
import com.valerehealth.brightree.repository.RequestLogRepository;

@Service
public class RequestLogService {

    @Autowired
    RequestLogRepository requestLogRepository;

    public RequestLog createRquestLog(String endpoint, UUID orderId, String requestIn) {
        RequestLog requestLog = new RequestLog();
        requestLog.setEndpoint(endpoint);
        requestLog.setOrderId(orderId);
        requestLog.setRequestIn(requestIn);
        Instant now = Instant.now();
        requestLog.setCreatedAt(now);
        requestLog.setUpdatedAt(now);
        return requestLogRepository.save(requestLog);
    }

    public RequestLog updateRquestLog(RequestLog requestLog) {
        requestLog.setUpdatedAt(Instant.now());
        return requestLogRepository.save(requestLog);
    }

}
