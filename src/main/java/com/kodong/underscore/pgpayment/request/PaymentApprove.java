package com.kodong.underscore.pgpayment.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentApprove {
    private String paymentKey;
    private String orderId;
    private int amount;

    public PaymentApprove(String orderId, String paymentKey, int amount) {
        this.orderId = orderId;
        this.paymentKey = paymentKey;
        this.amount = amount;
    }
}
