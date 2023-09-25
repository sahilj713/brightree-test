package com.valerehealth.brightree.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.valerehealth.brightree.model.entity.OrderIdentifier;

public interface OrderIdentifierRepository extends JpaRepository<OrderIdentifier, Integer> {

    OrderIdentifier findByOrderId(UUID orderId);

    OrderIdentifier findByReferralKey(Integer referralKey);

    OrderIdentifier findByGeneratedOrderId(String generatedOrderId);

    OrderIdentifier findByGeneratedPatientId(String generatedPatientId);

}
