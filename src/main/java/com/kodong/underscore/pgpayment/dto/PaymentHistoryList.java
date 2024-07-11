package com.kodong.underscore.pgpayment.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter @Setter
public class PaymentHistoryList {
    private Page<PaymentHistoryDto> paymentHistoryList;
    private int totalPayments;

    public PaymentHistoryList(Page<PaymentHistoryDto> paymentHistoryList, int totalPayments) {
        this.paymentHistoryList = paymentHistoryList;
        this.totalPayments = totalPayments;

    }
}
