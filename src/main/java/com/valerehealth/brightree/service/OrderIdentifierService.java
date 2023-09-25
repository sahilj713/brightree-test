package com.valerehealth.brightree.service;

import java.time.Instant;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.valerehealth.brightree.model.entity.OrderIdentifier;
import com.valerehealth.brightree.model.valere.ValereOrder;
import com.valerehealth.brightree.repository.OrderIdentifierRepository;
import com.valerehealth.brightree.util.StringsUtil;

@Service
public class OrderIdentifierService {

    @Autowired
    OrderIdentifierRepository orderIdentifierRepository;

    public OrderIdentifier createOrderIdentifier(ValereOrder valereOrder) {
        OrderIdentifier orderIdentifier =
                orderIdentifierRepository.findByOrderId(valereOrder.getId());

        if (orderIdentifier == null) {
            return orderIdentifierRepository
                    .save(new OrderIdentifier(valereOrder.getId(), valereOrder.getOriginatorId(),
                            generateRandomOrderId(), generateRandomPatientId(),
                            valereOrder.getOriginatorOrderId(), valereOrder.getPatient().getId()));
        }

        // Duplicate
        if (orderIdentifier.getReferralKey() != null) {
            return null;
        }

        return orderIdentifier;
    }

    public OrderIdentifier getOrderIdentifier(UUID orderId) {
        return orderIdentifierRepository.findByOrderId(orderId);
    }

    public OrderIdentifier getOrderIdentifierByReferralKey(Integer referralKey) {
        return orderIdentifierRepository.findByReferralKey(referralKey);
    }

    public OrderIdentifier updateOrderIdentifier(OrderIdentifier orderIdentifier) {
        orderIdentifier.setUpdatedAt(Instant.now());
        return orderIdentifierRepository.save(orderIdentifier);
    }

    private String generateRandomOrderId() {
        String candidateOrderId = StringsUtil.randomString(20);

        while (orderIdentifierRepository.findByGeneratedOrderId(candidateOrderId) != null) {
            candidateOrderId = StringsUtil.randomString(20);
        }

        return candidateOrderId;
    }

    private String generateRandomPatientId() {
        String candidatePatientId = StringsUtil.randomString(20);

        while (orderIdentifierRepository.findByGeneratedPatientId(candidatePatientId) != null) {
            candidatePatientId = StringsUtil.randomString(20);
        }

        return candidatePatientId;
    }

}
