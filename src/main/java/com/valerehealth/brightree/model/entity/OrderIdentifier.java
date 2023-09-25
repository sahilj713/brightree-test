package com.valerehealth.brightree.model.entity;

import java.time.Instant;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

// We create an orderIdentifier for the incoming valereOrders.
// It's a map of IDs for each of the orders.
@Data
@Entity
@Table(name = "order_identifiers")
public class OrderIdentifier {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "order_id")
    private UUID orderId;

    @Column(name = "originator_id")
    private String originatorId;

    @Column(name = "generated_order_id")
    private String generatedOrderId;

    @Column(name = "generated_patient_id")
    private String generatedPatientId;

    @Column(name = "originator_order_id")
    private String originatorOrderId;

    @Column(name = "originator_patient_id")
    private String originatorPatientId;

    @Column(name = "referral_key")
    private Integer referralKey;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    public OrderIdentifier() {}

    public OrderIdentifier(UUID orderId, String originatorId, String generatedOrderId,
            String generatedPatientId, String originatorOrderId, String originatorPatientId) {

        this.orderId = orderId;
        this.originatorId = originatorId;
        this.generatedOrderId = generatedOrderId;
        this.generatedPatientId = generatedPatientId;
        this.originatorOrderId = originatorOrderId;
        this.originatorPatientId = originatorPatientId;
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

}
