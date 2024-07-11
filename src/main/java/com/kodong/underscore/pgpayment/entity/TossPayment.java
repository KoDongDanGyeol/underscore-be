package com.kodong.underscore.pgpayment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
@AllArgsConstructor
@Builder
public class TossPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String paymentKey;
    private String orderId;
    private String orderName;
    private String method;
    private int amount;
    private LocalDate approvedAtDate;
    private String approvedAtTime;
    private String paymentStatus;
    private LocalDate billingDate;
    private String receiptUrl;


    public TossPayment() {

    }
}
