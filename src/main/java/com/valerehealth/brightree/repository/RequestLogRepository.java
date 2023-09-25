package com.valerehealth.brightree.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.valerehealth.brightree.model.entity.RequestLog;

public interface RequestLogRepository extends JpaRepository<RequestLog, Integer> {

    // Could be null
    RequestLog findByOrderId(UUID orderId);

}
