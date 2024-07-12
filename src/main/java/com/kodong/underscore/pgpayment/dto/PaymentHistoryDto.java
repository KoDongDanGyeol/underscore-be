package com.kodong.underscore.pgpayment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@Builder
public class PaymentHistoryDto {
    private String orderId;
    private String orderName;
    private String method;
    private LocalDate approvedAtDate;
    private String paymentStatus;
    private LocalDate billingDate;
    private int amount;
    private String receiptUrl;



}
