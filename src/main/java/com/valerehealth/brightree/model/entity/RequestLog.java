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

@Data
@Entity
@Table(name = "request_logs")
public class RequestLog {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "order_id")
    private UUID orderId;

    @Column(name = "endpoint")
    private String endpoint;

    @Column(name = "request_in")
    private String requestIn;

    @Column(name = "request_out")
    private String requestOut;

    @Column(name = "response_in")
    private String responseIn;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

}
